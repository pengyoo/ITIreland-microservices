package works.itireland.clients.post;



import lombok.Data;

@Data
public class PostRequest {
    private Long userId;
    private String title;
    private String content;

}

