package works.itireland.user.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import works.itireland.clients.user.UserLoginResponse;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;
import works.itireland.clients.user.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserResponse register(UserRegisterRequest userRegisterRequest);

    List<UserResponse> findAll(Pageable pageable);

    UserResponse find(Long userId);



    UserResponse findByUsername(String username);

    UserResponse findByUsernameAndPassword(String username, String password);

    UserLoginResponse login(String username);


    UserResponse update(UserUpdateRequest userUpdateRequest);

    void delete(long id) throws Throwable;

    long count();
}
