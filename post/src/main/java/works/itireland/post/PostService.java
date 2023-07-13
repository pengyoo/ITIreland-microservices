package works.itireland.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import works.itireland.post.domain.Post;

public interface PostService {
    PostResponse insert(PostRequest postRequest);

    Page<PostResponse> findAll(Pageable pageable);
}
