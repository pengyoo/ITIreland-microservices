package works.itireland.post;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import works.itireland.post.domain.Post;
import works.itireland.user.UserClient;

import java.util.function.Function;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserClient userClient;
    private final PostRepository postRepository;
    @Override
    public PostResponse insert(PostRequest postRequest) {
        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);
        post = postRepository.insert(post);
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);
        return postResponse;
    }

    @Override
    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(new Function<Post, PostResponse>() {
            @Override
            public PostResponse apply(Post post) {
                PostResponse postResponse = new PostResponse();
                BeanUtils.copyProperties(post, postResponse);
                postResponse.setUser(userClient.find(post.getUserId()));
                return postResponse;
            }
        });
    }
}
