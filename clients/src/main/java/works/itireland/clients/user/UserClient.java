package works.itireland.clients.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import works.itireland.clients.R;

import java.util.List;

@FeignClient(
        value = "user",
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

    @GetMapping("/login")
    R<UserResponse> findByUsernameAndPassword(@RequestParam("username") String username,
                                              @RequestParam("password") String password);
}
