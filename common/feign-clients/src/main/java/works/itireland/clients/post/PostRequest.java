package works.itireland.clients.post;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    @JsonIgnore
    private Long userId;
    private String title;
    private String content;
    private List<String> tags;
    private String category;

}

