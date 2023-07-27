package works.itireland.post.comment;

import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import works.itireland.post.post.Post;

import java.time.LocalDateTime;

@Document("comment")
public class Comment {
    @Id
    private String id;
    private String content;
    private LocalDateTime ctime;
    private LocalDateTime utime;

    // 0 normal, -1 delete
    private int state;
    private Long userId;
    private Comment parentComment;

    @DBRef
    private Post post;
}
