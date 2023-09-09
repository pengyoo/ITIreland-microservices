package works.itireland.clients.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username can't be null!")
    private String username;

    private String email;

    @NotBlank(message = "Password can't be null!")
    private String password;
}
