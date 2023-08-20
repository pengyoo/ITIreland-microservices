package works.itireland.post.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @Indexed(unique = true)
    private String tag;

    // 0 normal, -1 delete
    private int state;


}
