package works.itireland.user;

import org.springframework.data.domain.Pageable;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(UserRegisterRequest userRegisterRequest);

    List<UserResponse> findAll(Pageable pageable);

    UserResponse find(Long userId);

    List<UserResponse> findFollowingUsers(Long userId, Pageable pageable);

    void follow(Long userId, Long followingUserId);

    void unFollow(Long userId, Long followingUserId);

    List<UserResponse> findFollowerUsers(Long userId, Pageable pageable);

    boolean isFollowing(Long userId, Long followingUserId);

    UserResponse findByUsername(String username);

    UserResponse findByUsernameAndPassword(String username, String password);
}
