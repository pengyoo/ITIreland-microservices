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

    @GetMapping("/test")
    @RolesAllowed("USER")
    public R<AuthResponse> test(){
        return R.success(null);
    }
}
