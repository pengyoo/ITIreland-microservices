package works.itireland.post.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Tag {

    @Id
    private String id;
    @Indexed(unique = true)
    private String tag;

    // 0 normal, -1 delete
    private int state;
}
