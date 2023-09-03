package works.itireland.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import works.itireland.clients.user.UserClient;
import works.itireland.clients.user.UserLoginResponse;
import works.itireland.clients.user.UserResponse;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserClient userClient;

    @Bean
    public AuthenticationProvider authenticationProvider() throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserLoginResponse userResponse = userClient.login(username).getData();
                if(userResponse == null) {
                    throw new UsernameNotFoundException("Username doesn't exist.");
                }
                UserDetails userDetails = new UserDetails();
                BeanUtils.copyProperties(userResponse, userDetails);
                userDetails.setRole(Role.valueOf(userResponse.getRole()));
                return userDetails;
            }
        };

//        return username -> {
//
//            UserLoginResponse userResponse = userClient.login(username).getData();
//            if(userResponse == null) {
//                throw new UsernameNotFoundException("Username doesn't exist.");
//            }
//            UserDetails userDetails = new UserDetails();
//            BeanUtils.copyProperties(userResponse, userDetails);
//            userDetails.setRole(Role.valueOf(userResponse.getRole()));
//            return userDetails;
//        };

    }

}
