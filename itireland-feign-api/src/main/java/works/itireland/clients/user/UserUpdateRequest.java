package works.itireland.clients.user;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private Long id;
    private String username;
//    private String password;
    private String email;
    private String profile;
    private String location;
    private String role;
    private int credits;
    private int state;
    private int level;
}
