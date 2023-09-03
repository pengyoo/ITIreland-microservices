package works.itireland.user.service;

import org.springframework.data.domain.Pageable;
import works.itireland.clients.user.UserLoginResponse;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(UserRegisterRequest userRegisterRequest);

    List<UserResponse> findAll(Pageable pageable);

    UserResponse find(Long userId);



    UserResponse findByUsername(String username);

    UserResponse findByUsernameAndPassword(String username, String password);

    UserLoginResponse login(String username);
}
