package works.itireland.user;


import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.AuthorizedRoles;
import works.itireland.clients.R;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public R<UserResponse> save(@RequestBody UserRegisterRequest userRegisterRequest){
        log.info("register user {}", userRegisterRequest);
        return R.success(userService.register(userRegisterRequest));
    }

    @GetMapping("/{userId}")
    public R<UserResponse> find(@PathVariable Long userId){
        log.info("find user by userId: {}", userId);
        return R.success(userService.find(userId));
    }

    @GetMapping("/findByUsername/{username}")
    public R<UserResponse> findByUsername(@PathVariable String username){
        log.info("find user by username: {}", username);
        return R.success(userService.findByUsername(username));
    }

    @GetMapping("/login")
    public R<UserResponse> findByUsernameAndPassword(@RequestParam String username,
                                                     @RequestParam String password){
        log.info("find user by username: {}", username);
        return R.success(userService.findByUsernameAndPassword(username, password));
    }

    @GetMapping
    public R<List<UserResponse>> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find users by page: {}, pageSize: {}", page, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(userService.findAll(pageable));
    }


    @GetMapping("/{userId}/followings")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R<List<UserResponse>> followings(@PathVariable Long userId,
                                                @RequestParam(required = false, defaultValue = "0") int page,
                                                @RequestParam(required = false, defaultValue = "1000") int pageSize){
        log.info("find following users by userId{}", userId);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(userService.findFollowingUsers(userId, pageable));
    }

    @GetMapping("/{userId}/followers")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R<List<UserResponse>> followers(@PathVariable Long userId,
                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                   @RequestParam(required = false, defaultValue = "1000") int pageSize){
        log.info("find follower users by userId{}", userId);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(userService.findFollowerUsers(userId, pageable));
    }

    @PostMapping("/follow/{userId}/{followingUserId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R follow(@PathVariable("userId") Long userId, @PathVariable("followingUserId") Long followingUserId){
        log.info("follow user: userId {}, followingUserId {}", userId, followingUserId);
        userService.follow(userId, followingUserId);
        return R.success(null);
    }

    @PostMapping("/unFollow/{userId}/{followingUserId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R unFollow(@PathVariable("userId") Long userId, @PathVariable("followingUserId") Long followingUserId){
        log.info("unFollow user: userId {}, followingUserId {}", userId, followingUserId);
        userService.unFollow(userId, followingUserId);
        return R.success(null);
    }

    @GetMapping("/isFollowing/{userId}/{followingUserId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R isFollowing(@PathVariable("userId") Long userId, @PathVariable("followingUserId") Long followingUserId){
        return R.success(userService.isFollowing(userId, followingUserId));
    }
}
