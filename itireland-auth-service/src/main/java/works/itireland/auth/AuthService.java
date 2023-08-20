package works.itireland.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public List<String> getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List list = new ArrayList();
        for(GrantedAuthority authority : authorities) {
            list.add(authority.getAuthority());
        }
        return list;
    }
}
