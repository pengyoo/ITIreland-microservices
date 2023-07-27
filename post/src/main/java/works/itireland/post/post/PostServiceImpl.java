package works.itireland.post.post;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import works.itireland.clients.post.PostRequest;
import works.itireland.clients.post.PostResponse;
import works.itireland.clients.user.UserClient;
import works.itireland.post.category.CategoryRepository;
import works.itireland.post.tag.Tag;
import works.itireland.post.tag.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserClient userClient;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    @Override
    public PostResponse insert(PostRequest postRequest) {
        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);
        // Process Category
        post.setCategory(categoryRepository.findById(postRequest.getCatgoryRequest()).get());
        //TODO: Store Tag
        List<Tag> tags = new ArrayList<>();
        for(String tag : postRequest.getTagsRequest()) {
            Tag t = tagRepository.findById(tag).orElse(null);
            if(t == null) {
                tags.add(tagRepository.insert(new Tag(tag, 0)));
            } else {
                tags.add(t);
            }
        }
        post.setTags(tags);

        // Store post
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

                // Process Category
                postResponse.setCategory(post.getCategory().getCategory());

                // Process Tags
                List<String> tags = new ArrayList<>();
                for(Tag tag : post.getTags()){
                    tags.add(tag.getTag());
                }
                postResponse.setTags(tags);

                postResponse.setUser(userClient.find(post.getUserId()));
                return postResponse;
            }
        });
    }
}
