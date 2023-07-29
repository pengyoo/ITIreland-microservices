package works.itireland.post.vote;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import works.itireland.post.category.Category;
import works.itireland.post.post.Post;

@Data
@Document("upvote")
@CompoundIndex(name = "userId_post_idx", def = "{'userId': 1, 'post': 1}", unique = true)
public class Upvote {

    @Id
    private String id;
    private Long userId;
    @DBRef(lazy = false)
    private Post post;

    public Upvote(Long userId, Post post) {
        this.userId = userId;
        this.post = post;
    }

    public Upvote() {
    }
}
