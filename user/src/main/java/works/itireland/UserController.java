package works.itireland;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import works.itireland.user.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User save(@RequestBody UserRegisterRequest userRegisterRequest){
        return userService.register(userRegisterRequest);
    }

    @GetMapping("/{userId}")
    public UserResponse find(@PathVariable Long userId){
        return userService.find(userId);
    }

    @GetMapping
    public List<UserResponse> list(@RequestParam int page, @RequestParam int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return userService.findAll(pageable);
    }
}
