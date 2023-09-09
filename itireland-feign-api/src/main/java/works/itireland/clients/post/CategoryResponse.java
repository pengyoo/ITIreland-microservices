package works.itireland.clients.post;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {

    public CategoryResponse(String id, int state, int sort) {
        this.id = id;
        this.state = state;
        this.sort = sort;
        this.category = id;
    }

    private String id;
    private int state;
    private int sort;

    private String category;

    public String getCategory() {
        return id;
    }
}
