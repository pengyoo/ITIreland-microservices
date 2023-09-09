package works.itireland.clients.post;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.AuthorizedRoles;
import works.itireland.clients.R;

import java.util.List;

@FeignClient(
        contextId = "comment-client",
        value = "post-service",
        path = "api/v1/comments"
)
public interface CommentClient {

    @GetMapping("/all")
    public R<List<CommentResponse>> findAllComments(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                    @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
                                                    @RequestParam(value = "sort", defaultValue = "ctime", required = false) String sort
    );

    @GetMapping("/count")
    public long count();

    @GetMapping("/{id}")
    public R<CommentResponse> getById(@PathVariable("id") String id);
    @PostMapping
    public R<CommentResponse> save(@RequestBody CommentRequest commentRequest);
}
