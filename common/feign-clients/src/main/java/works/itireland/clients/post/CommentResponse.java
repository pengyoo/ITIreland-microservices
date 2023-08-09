package works.itireland.clients.post;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import works.itireland.clients.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentResponse {

    private String id;
    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime utime;

    private UserResponse user;

    private String postId;

    private List<CommentResponse> childrenComments;

}
