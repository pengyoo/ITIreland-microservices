package works.itireland;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String profile;
    private String location;
}
