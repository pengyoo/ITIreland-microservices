package works.itireland.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.R;
import works.itireland.clients.auth.AuthClient;
import works.itireland.clients.auth.AuthResponse;
import works.itireland.clients.auth.LoginRequest;
import works.itireland.exception.BadCredentialException;

@RestController
@RequestMapping("/api/v1/admin/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail().split("@")[0];
        loginRequest.setUsername(username);
        R<AuthResponse> login = authClient.login(loginRequest);
        return new ResponseEntity<>(login.getData(), HttpStatus.OK);
    }
}
