package works.itireland.post.domain;

import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("category")
public class Category {
    @Id
    private String category;

    // 0 normal, -1 delete
    private int state;

    private int sort;
}
