package works.itireland.post.comment;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import works.itireland.post.post.Post;

import java.time.LocalDateTime;
import java.util.List;

@Document("comment")
@Data
public class Comment {
    @Id
    private String id;
    private String content;
    private LocalDateTime ctime;
    private LocalDateTime utime;

    // 0 normal, -1 delete
    private int state;
    private Long userId;

    @DBRef(lazy = false)
    private Comment parentComment;

    @DBRef(lazy = false)
    private List<Comment> comments;

    @DBRef(lazy = false)
    private Post post;
}
