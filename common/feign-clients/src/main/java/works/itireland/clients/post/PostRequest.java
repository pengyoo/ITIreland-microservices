package works.itireland.clients.post;



import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    private Long userId;
    private String title;
    private String content;
    private List<String> tags;
    private String category;

}

