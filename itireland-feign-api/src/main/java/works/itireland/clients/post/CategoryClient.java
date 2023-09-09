package works.itireland.clients.post;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.AuthorizedRoles;
import works.itireland.clients.R;

import java.util.List;

@FeignClient(
        contextId = "category-client",
        value = "post-service",
        path = "api/v1/categories"
)
public interface CategoryClient {

    @GetMapping
    public R<List<CategoryResponse>> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize);

    @GetMapping("/count")
    public long count();
    @PostMapping
    public R<CategoryResponse> save(@RequestBody CategoryRequest categoryRequest);

    @GetMapping("/{id}")
    public R<CategoryResponse> get(@PathVariable(value = "id") String id);
}
