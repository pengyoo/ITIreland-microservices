package works.itireland.clients.tag;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagResponse {
    private String tag;
}
