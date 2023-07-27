package works.itireland.user;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import works.itireland.amqp.RabbitMQMessageProducer;
import works.itireland.clients.notification.NotificationClient;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    private final NotificationClient notificationClient;

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
        log.info("publishing {} to {}", notification, "internal.notification.routing-key");
        // Send message to RabbitMQ
        rabbitMQMessageProducer.publish(notification,
                "internal.exchange",
                "internal.notification.routing-key");
        log.info("published {} to {}", notification, "internal.notification.routing-key");

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);

        return userResponse;
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
    public List<UserResponse> findFollowingUsers(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow();
        return followingRepository.findFollowingsByUser(user, pageable)
                .stream()
                .map( user1 -> {
                  UserResponse userResponse = new UserResponse();
                  BeanUtils.copyProperties(user1, userResponse);
                  return userResponse;
                }).toList();
    }
}
