package works.itireland;

import works.itireland.domain.Post;
import works.itireland.payload.PostResponse;

public interface PostService {
    PostResponse insert(Post post);
}
