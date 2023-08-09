package works.itireland.post.comment;


import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.AuthorizedRoles;
import works.itireland.clients.R;
import works.itireland.clients.post.CommentRequest;
import works.itireland.clients.post.CommentResponse;
import works.itireland.exception.ValidationException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R save(@Validated @RequestBody CommentRequest commentRequest, BindingResult errors){
        //Throw Validation Exception
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
        return R.success(commentService.save(commentRequest));
    }

    @GetMapping("/{postId}")
    public R findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                     @RequestParam(defaultValue = "100", required = false) Integer size,
                     @RequestParam(defaultValue = "ctime", required = false) String sort,
                     @PathVariable String postId
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        List<CommentResponse> comments = commentService.findAllByPostId(postId, pageable);
        return R.success(comments);

    }

    @DeleteMapping("/{userId}/{commentId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R delete(@PathVariable Long userId, @PathVariable String commentId){
        commentService.delete(userId, commentId);
        return R.success(null);
    }
}
