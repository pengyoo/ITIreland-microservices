package works.itireland.user.service;


import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import works.itireland.amqp.RabbitMQMessageProducer;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.user.UserLoginResponse;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;
import works.itireland.exception.ApiRequestException;
import works.itireland.user.domain.Image;
import works.itireland.user.domain.User;
import works.itireland.user.repository.ImageRepository;
import works.itireland.user.repository.UserRepository;
import works.itireland.user.s3.S3Buckets;
import works.itireland.user.s3.S3Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    private final ImageRepository imageRepository;

    @Override
    public UserResponse register(UserRegisterRequest userRegisterRequest) {
        User user = User.builder()
                .email(userRegisterRequest.getEmail())
                .username(userRegisterRequest.getUsername())
                .password(userRegisterRequest.getPassword())
                .profile(userRegisterRequest.getProfile())
                .location(userRegisterRequest.getLocation())
                .ctime(LocalDateTime.now())
                .state(0)
                .credits(0)
                .level(0)
                .role("USER")
                .build();

        user = userRepository.save(user);
        var notification = NotificationRequest.builder()
                .fromUsername(null)
                .message("Welcome to IT Ireland!")
                .toUserId(user.getId())
                .toUserEmail(user.getEmail())
                .toUserName(user.getUsername())
                .type(0)
                .ctime(LocalDateTime.now())
                .state(0)
                .build();

        // Send Notification through rabbitmq
        sendMessageToRabbitMQ(notification);

        return getUserResponse(user);
    }

    // Add resilience4j circuit breaker
    @Retry(name = "default", fallbackMethod = "")
    public void sendMessageToRabbitMQ(NotificationRequest notification) {
        log.info("publishing {} to {}", notification, "internal.notification.routing-key");
        // Send message to RabbitMQ
        rabbitMQMessageProducer.publish(notification,
                "internal.exchange",
                "internal.notification.routing-key");
        log.info("published {} to {}", notification, "internal.notification.routing-key");
    }

    @Override
    public List<UserResponse> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        log.info("finding users  by pageable {}", pageable);
        return users.stream().map((user) -> UserResponse.builder()
                        .id(user.getId())
                        .level(user.getLevel())
                        .profile(user.getProfile())
                        .location(user.getLocation())
                        .email(user.getEmail())
                        .credits(user.getCredits())
                        .username(user.getUsername())
                        .ctime(user.getCtime())
                        .state(user.getState())
                        .headShotUrl(user.getHeadShotUrl())
                        .build()).toList();
    }

    @Override
    public UserResponse find(Long userId) {
        log.info("finding user by id {}", userId);
        return userRepository.findById(userId).map(new Function<User, UserResponse>() {
            @Override
            public UserResponse apply(User user) {
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(user, userResponse);
                return userResponse;
            }
        }).orElseThrow();
    }



    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return getUserResponse(user);
    }

    @Override
    public UserResponse findByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password).orElseThrow();
        return getUserResponse(user);
    }

    @Override
    public UserLoginResponse login(String username) {
        User user = userRepository.findByUsername(username);
        return getUserLoginResponse(user);
    }

    private UserResponse getUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    private UserLoginResponse getUserLoginResponse(User user) {
        UserLoginResponse userResponse = new UserLoginResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }



}
