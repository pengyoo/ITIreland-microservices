package works.itireland.post;



import lombok.Data;
import works.itireland.user.UserResponse;

import java.time.LocalDateTime;

@Data
public class PostRequest {
    private Long userId;
    private String title;
    private String content;

}

