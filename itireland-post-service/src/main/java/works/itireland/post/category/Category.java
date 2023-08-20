package works.itireland.post.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private String category;

    // 0 normal, -1 delete
    private int state;

    private int sort;

}
