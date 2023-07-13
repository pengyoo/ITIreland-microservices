package works.itireland.post.domain;


import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("post")
public class Post {
    @Id
    private String id;


    private Long userId;
    private String title;

    private String content;

    private LocalDateTime ctime;
    private LocalDateTime utime;
    private int views;


    private int upvotes = 0;
    private int downvotes = 0;

    // 0 normal, -1 delete
    private int state;

    @DBRef(lazy = false)
    private List<Tag> tags;

    @ReadOnlyProperty
    @DBRef(lazy = true)
    private List<Comment> comments;

    @DBRef(lazy = false)
    private Category category;

}
