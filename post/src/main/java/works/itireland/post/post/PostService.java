package works.itireland.post.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import works.itireland.clients.post.PostRequest;
import works.itireland.clients.post.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse insert(PostRequest postRequest);

    Page<PostResponse> findAll(Pageable pageable);

    List<PostResponse> findAllByUserId(Long userId, Pageable pageable);

    List<PostResponse> findFollowingsByUserId(Long userId, Pageable pageable);

    void delete(Long userId, String postId);

    int upvote(Long userId, String postId);

    int unUpvote(Long userId, String postId);

    PostResponse findById(String postId);
}
