package works.itireland.clients.post;



import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    private Long userId;
    private String title;
    private String content;
    private List<String> tagsRequest;
    private String catgoryRequest;

}

