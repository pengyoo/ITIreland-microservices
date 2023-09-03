package works.itireland.user.service;

import org.springframework.data.domain.Pageable;
import works.itireland.clients.user.UserResponse;

import java.util.List;

public interface FollowService {
    List<UserResponse> findFollowingUsers(Long userId, Pageable pageable);

    void follow(Long userId, Long followingUserId);

    void unFollow(Long userId, Long followingUserId);

    List<UserResponse> findFollowerUsers(Long userId, Pageable pageable);

    boolean isFollowing(Long userId, Long followingUserId);
}
