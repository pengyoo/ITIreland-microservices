package works.itireland.user.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.AuthUtils;
import works.itireland.auth.AuthorizedRoles;
import works.itireland.clients.R;
import works.itireland.clients.user.UserResponse;
import works.itireland.user.service.FollowService;
import works.itireland.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    @GetMapping("/{userId}/followings")
    public R<List<UserResponse>> followings(@PathVariable Long userId,
                                            @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "1000") int pageSize) {
        log.info("find following users by userId{}", userId);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(followService.findFollowingUsers(userId, pageable));
    }

    @GetMapping("/{userId}/followers")
    public R<List<UserResponse>> followers(@PathVariable Long userId,
                                           @RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "1000") int pageSize) {
        log.info("find follower users by userId{}", userId);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(followService.findFollowerUsers(userId, pageable));
    }

    @PostMapping("/secure/follow/{followingUserId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R follow(HttpServletRequest request, @PathVariable("followingUserId") Long followingUserId) {
        String username = AuthUtils.getUserName(request);
        UserResponse user = userService.findByUsername(username);
        log.info("follow user: userId {}, followingUserId {}", user.getId(), followingUserId);
        followService.follow(user.getId(), followingUserId);
        return R.success(null);
    }

    @PostMapping("/secure/unFollow/{followingUserId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R unFollow(HttpServletRequest request, @PathVariable("followingUserId") Long followingUserId) {
        String username = AuthUtils.getUserName(request);
        UserResponse user = userService.findByUsername(username);
        log.info("unFollow user: userId {}, followingUserId {}", user.getId(), followingUserId);
        followService.unFollow(user.getId(), followingUserId);
        return R.success(null);
    }

    @GetMapping("/secure/isFollowing/{followingUserId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R isFollowing(HttpServletRequest request, @PathVariable("followingUserId") Long followingUserId) {

        String username = AuthUtils.getUserName(request);
        UserResponse user = userService.findByUsername(username);
        log.info("check if user: userId {}, is following UserId {}", user.getId(), followingUserId);
        return R.success(followService.isFollowing(user.getId(), followingUserId));
    }
}
