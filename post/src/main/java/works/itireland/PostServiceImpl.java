package works.itireland;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import works.itireland.domain.Post;
import works.itireland.payload.PostResponse;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    @Override
    public PostResponse insert(Post post) {
        post = postRepository.insert(post);
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);
        return postResponse;
    }
}
