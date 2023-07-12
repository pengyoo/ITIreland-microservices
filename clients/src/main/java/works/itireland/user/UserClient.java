package works.itireland.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "user",
        path = "api/v1/users"
)
public interface UserClient {

    @GetMapping( "/{userId}")
    UserResponse find(
            @PathVariable("userId") Long userId);

    @GetMapping
    UserResponse list();

}
