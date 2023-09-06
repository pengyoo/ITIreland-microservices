package works.itireland.clients.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.R;

import javax.xml.stream.events.Characters;
import java.util.List;

@FeignClient(
        value = "user-service",
        path = "api/v1/users"
)
public interface UserClient {

    @GetMapping( "/{userId}")
    R<UserResponse> find(
            @PathVariable("userId") Long userId);


    @GetMapping
    R<UserResponse> list();

    @GetMapping("/{userId}/followings")
    R<List<UserResponse>> findFollowingUsers(@PathVariable("userId") Long userId);


    @GetMapping("/findByUsername/{username}")
    R<UserResponse> findByUsername(@PathVariable("username") String username);



    @PostMapping("/login")
    R<UserLoginResponse> login(@RequestParam("username") String username);

    @PostMapping
    @PatchMapping
    R<UserResponse> register(@RequestBody UserRegisterRequest registerRequest);

    @GetMapping
    public R<List<UserResponse>> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize);

}
