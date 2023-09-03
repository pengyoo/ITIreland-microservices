package works.itireland.auth;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.exception.ValidationException;
import works.itireland.clients.R;
import works.itireland.clients.auth.AuthResponse;
import works.itireland.clients.auth.LoginRequest;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;

import java.util.List;


@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public R<AuthResponse> login(@Validated @RequestBody LoginRequest loginRequest, BindingResult errors){
        //Throw Validation Exception
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
        return R.success(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public R<UserResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){
        UserResponse userResponse = authService.register(userRegisterRequest);
        return R.success(userResponse);
    }

    @GetMapping("/check")
    @RolesAllowed("USER")
    public R<AuthResponse> check(){
        return R.success(null);
    }


    @GetMapping("/roles")
    public R<List<String>> roles() {
        return R.success(authService.getRole());
    }
}
