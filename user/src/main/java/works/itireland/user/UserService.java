package works.itireland.user;

import org.springframework.data.domain.Pageable;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(UserRegisterRequest userRegisterRequest);

    List<UserResponse> findAll(Pageable pageable);

    UserResponse find(Long userId);
}
