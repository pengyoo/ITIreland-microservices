package works.itireland.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import works.itireland.clients.user.UserResponse;
import works.itireland.user.domain.Following;
import works.itireland.user.domain.User;
import works.itireland.user.repository.FollowingRepository;
import works.itireland.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;


    @Override
    public List<UserResponse> findFollowingUsers(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow();
        return followingRepository.findFollowingsByUser(user, pageable)
                .stream()
                .map(user1 -> getUserResponse(user1)).toList();
    }


    @Override
    public List<UserResponse> findFollowerUsers(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow();
        return followingRepository.findFollowersByUser(user, pageable)
                .stream()
                .map(user1 -> getUserResponse(user1))
                .toList();
    }


    @Override
    public void follow(Long userId, Long followingUserId) {
        User user = userRepository.findById(userId).orElseThrow();
        User followingUser = userRepository.findById(followingUserId).orElseThrow();
        Following following = new Following(followingUser, user, LocalDateTime.now());
        followingRepository.save(following);
    }

    @Override
    public void unFollow(Long userId, Long followingUserId) {
        User user = userRepository.findById(userId).orElseThrow();
        User followingUser = userRepository.findById(followingUserId).orElseThrow();
        Following following = followingRepository.findByFollowingAndFollower(followingUser, user).orElseThrow();
        followingRepository.delete(following);
    }

    @Override
    public boolean isFollowing(Long userId, Long followingUserId) {
        User user = userRepository.findById(userId).orElseThrow();
        User followingUser = userRepository.findById(followingUserId).orElseThrow();
        Following following = followingRepository.findByFollowingAndFollower(followingUser, user).orElse(null);
        return following != null;
    }

    private UserResponse getUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }
}
