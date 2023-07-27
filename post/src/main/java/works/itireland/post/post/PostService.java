package works.itireland.post.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import works.itireland.clients.post.PostRequest;
import works.itireland.clients.post.PostResponse;

public interface PostService {
    PostResponse insert(PostRequest postRequest);

    Page<PostResponse> findAll(Pageable pageable);
}
