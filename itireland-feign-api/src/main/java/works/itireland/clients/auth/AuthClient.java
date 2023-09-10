package works.itireland.clients.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import works.itireland.clients.R;
import works.itireland.clients.config.FeignClientConfig;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;
import works.itireland.clients.user.UserUpdateRequest;

import java.util.List;

@FeignClient(
        value = "auth-service",
        path = "api/v1/auth",
        configuration = FeignClientConfig.class
)
public interface AuthClient {

    @PostMapping("/login")
    R<AuthResponse> login(@RequestBody LoginRequest loginRequest);

    @PostMapping("/register")
    public R<UserResponse> register(@RequestBody UserRegisterRequest userRegisterRequest);

    @PostMapping("/update")
    public R<UserResponse> update(@RequestBody UserUpdateRequest userUpdateRequest);

    @GetMapping("/roles")
    public R<List<String>> roles();
}

