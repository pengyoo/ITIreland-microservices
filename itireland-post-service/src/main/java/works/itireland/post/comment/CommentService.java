package works.itireland.post.comment;

import org.springframework.data.domain.Pageable;
import works.itireland.clients.post.CommentRequest;
import works.itireland.clients.post.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse save(CommentRequest commentRequest);
    List<CommentResponse> findAllByPostId(String postId, Pageable pageable);

    void delete(Long userId, String commentId);

    long count();

    List<CommentResponse> findAll(Pageable pageable);

    CommentResponse findById(String id);
}
