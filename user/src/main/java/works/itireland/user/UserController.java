package works.itireland.user;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
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
    public UserResponse save(@RequestBody UserRegisterRequest userRegisterRequest){
        log.info("register user" + userRegisterRequest);
        return userService.register(userRegisterRequest);
    }

    @GetMapping("/{userId}")
    public UserResponse find(@PathVariable Long userId){
        log.info("find user by userId:" + userId);
        return userService.find(userId);
    }

    @GetMapping
    public List<UserResponse> list(@RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find users by page:" + page+ ", pageSize:"+ pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return userService.findAll(pageable);
    }
}
