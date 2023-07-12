package works.itireland;

import org.springframework.data.domain.Pageable;
import works.itireland.user.UserResponse;

import java.util.List;

public interface UserService {
    User register(UserRegisterRequest userRegisterRequest);

    List<UserResponse> findAll(Pageable pageable);

    UserResponse find(Long userId);
}
