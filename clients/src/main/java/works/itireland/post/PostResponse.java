package works.itireland.post;



import lombok.Data;
import works.itireland.user.UserResponse;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private String id;
    private UserResponse user;
    private String title;
    private String content;

    private LocalDateTime ctime;
    private LocalDateTime utime;
    private int views;
    private int upvotes;
    private int downvotes;

    // 0 normal, -1 delete
    private int state;


}

