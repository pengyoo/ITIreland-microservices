package works.itireland.clients.post;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.R;

import java.util.List;

@FeignClient(
        contextId = "post-client",
        value = "post-service",
        path = "api/v1/posts"
)
public interface PostClient {
    @GetMapping
    public R<List<PostResponse>> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value="pageSize", required = false, defaultValue = "10") int pageSize);

    @GetMapping("/{postId}")
    public R<PostResponse> findById(@PathVariable("postId") String postId);

    @PostMapping
    public R<PostResponse> save(@RequestBody PostRequest postRequest);

    @GetMapping("/user/{userId}")
    public R<List<PostResponse>> findAllByUserId(@PathVariable("userId") Long userId, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(value="pageSize", required = false, defaultValue = "10") int pageSize);

    @GetMapping("/count")
    public long count();
}

