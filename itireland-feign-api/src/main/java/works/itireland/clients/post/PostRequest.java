package works.itireland.clients.post;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    private String id;
    @JsonIgnore
    private Long userId;
    private String title;
    private List<String> tags;
    private String category;

//    @JsonProperty("contentJson")
//    private JsonNode contentNode;

    private String content;

}

