package works.itireland.clients.post;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Comment content can't be null")
    private String content;

    @JsonIgnore
    private Long userId;
    @NotNull(message = "postId content can't be null")
    private String postId;
    private String parentId;
}
