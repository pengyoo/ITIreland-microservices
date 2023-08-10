package works.itireland.clients.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import works.itireland.clients.R;
import works.itireland.clients.config.FeignClientConfig;

import java.util.List;

@FeignClient(
        value = "auth-service",
        path = "api/v1/auth",
        configuration = FeignClientConfig.class
)
public interface AuthClient {

    @PostMapping("/login")
    R<AuthResponse> login(@RequestBody LoginRequest loginRequest);


    @GetMapping("/check")
    public R<AuthResponse> check(@RequestHeader("Authorization") String token);

    @GetMapping("/roles")
    public R<List<String>> roles();
}

