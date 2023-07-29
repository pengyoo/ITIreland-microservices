package works.itireland.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import works.itireland.auth.security.JwtService;
import works.itireland.auth.security.Role;
import works.itireland.auth.security.UserDetails;
import works.itireland.clients.auth.AuthResponse;
import works.itireland.clients.auth.LoginRequest;
import works.itireland.clients.user.UserClient;
import works.itireland.clients.user.UserResponse;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserClient userClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest loginRequest) {
//        String password = passwordEncoder.encode(loginRequest.getPassword());
        String password = loginRequest.getPassword();
        UserResponse userResponse =
                userClient.findByUsernameAndPassword(loginRequest.getUsername(), password)
                        .getData();
        if(userResponse == null) {
            throw new InsufficientAuthenticationException("Invalid username or password!");
        }
        UserDetails userDetails = new UserDetails();
        BeanUtils.copyProperties(userResponse, userDetails);
        userDetails.setRole(Role.valueOf(userResponse.getRole()));
        String jwtToken = jwtService.generateToken(userDetails);
        AuthResponse authResponse = getAuthResponse(userDetails);
        authResponse.setToken(jwtToken);
        return authResponse;
    }

    private AuthResponse getAuthResponse(UserDetails userDetails) {
        AuthResponse authResponse = new AuthResponse();
        BeanUtils.copyProperties(userDetails, authResponse);
        return authResponse;
    }
}
