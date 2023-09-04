package works.itireland.clients.post;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import works.itireland.clients.user.UserResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostResponse {
    private String id;
    private UserResponse user;
    private String title;
    private String content;

    private String category;

    private List<String> tags = new ArrayList<>();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime utime;
    private int views;
    private int upvotes;
    private int downvotes;

    // 0 normal, -1 delete
    private int state;

    private int commentCount;




}

