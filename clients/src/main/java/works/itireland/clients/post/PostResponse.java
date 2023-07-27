package works.itireland.clients.post;



import lombok.Data;
import works.itireland.clients.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private String id;
    private UserResponse user;
    private String title;
    private String content;

    private String category;

    private List<String> tags;

    private LocalDateTime ctime;
    private LocalDateTime utime;
    private int views;
    private int upvotes;
    private int downvotes;

    // 0 normal, -1 delete
    private int state;


}

