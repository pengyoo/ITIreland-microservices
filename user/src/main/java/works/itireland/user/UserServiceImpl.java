package works.itireland.user;


import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import works.itireland.notification.NotificationClient;
import works.itireland.notification.NotificationRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
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
                .type(0)
                .ctime(LocalDateTime.now())
                .state(0)
                .build();

        notificationClient.sendNotification(notification);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    @Override
    public List<UserResponse> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.stream().map(new Function<User, UserResponse>() {
            @Override
            public UserResponse apply(User user) {
                return UserResponse.builder()
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
                        .build();
            }
        }).toList();
    }

    @Override
    public UserResponse find(Long userId) {
        return userRepository.findById(userId).map(new Function<User, UserResponse>() {
            @Override
            public UserResponse apply(User user) {
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(user, userResponse);
                return userResponse;
            }
        }).orElseThrow();
    }
}
