package works.itireland.post.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import works.itireland.clients.post.PostRequest;
import works.itireland.clients.post.PostResponse;
import works.itireland.clients.user.UserClient;
import works.itireland.clients.user.UserResponse;
import works.itireland.post.category.CategoryRepository;
import works.itireland.exception.ApiRequestException;
import works.itireland.post.comment.CommentRepository;
import works.itireland.post.tag.Tag;
import works.itireland.post.tag.TagRepository;
import works.itireland.post.vote.Downvote;
import works.itireland.post.vote.DownvoteRepository;
import works.itireland.post.vote.Upvote;
import works.itireland.post.vote.UpvoteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService{

    private final UserClient userClient;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    private final TagRepository tagRepository;

    private final UpvoteRepository upvoteRepository;
    private final DownvoteRepository downvoteRepository;

    private final ObjectMapper objectMapper;

    @Override
    public PostResponse save(PostRequest postRequest) {
        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);

        // Process Category
        post.setCategory(categoryRepository.findById(postRequest.getCategory()).get());
        List<Tag> tags = new ArrayList<>();
        if(postRequest.getTags() != null) {
            for (String tag : postRequest.getTags()) {
                Tag t = tagRepository.findById(tag).orElse(null);
                if (t == null) {
                    tags.add(tagRepository.insert(new Tag(tag, 0)));
                } else {
                    tags.add(t);
                }
            }
            post.setTags(tags);
        }

        // Process JsonContent
        String contentJsonString = "";
        try {
            contentJsonString = objectMapper.writeValueAsString(postRequest.getContentNode());
        } catch (JsonProcessingException e) {
            throw new ApiRequestException(e.getMessage());
        }
        post.setContent(contentJsonString);

        // Set ctime and utime
        post.setCtime(LocalDateTime.now());
        post.setUtime(LocalDateTime.now());

        // Store post
        post = postRepository.save(post);
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);
        return postResponse;
    }

    @Override
    public void delete(Long userId, String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        if(post.getUserId() != userId){
            throw new ApiRequestException("The post doesn't belong to user whose userId is %s".formatted(userId));
        }
        post.setState(-1);
        postRepository.save(post);
    }


    @Override
    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findByState(pageable, 0)
                .map(post -> getPostResponse(post));
    }


    @Override
    public List<PostResponse> findAllByUserId(Long userId, Pageable pageable) {
        return postRepository.findByUserIdAndState(userId, 0, pageable)
                .stream()
                .map(post -> getPostResponse(post)).toList();
    }


    /**
     * Convert Post to PostResponse
     * @param post
     * @return
     */
    private PostResponse getPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);

        // Process Category
        postResponse.setCategory(post.getCategory().getCategory());

        // Process Tags
        if(post.getTags() != null) {
            List<String> tags = new ArrayList<>();
            for (Tag tag : post.getTags()) {
                tags.add(tag.getTag());
            }
            postResponse.setTags(tags);
        }

        // Count comments
        postResponse.setCommentCount(commentRepository.countByPost(post));

        // Process User
        postResponse.setUser(userClient.find(post.getUserId()).getData());
        return postResponse;
    }

    @Override
    public List<PostResponse> findFollowingsByUserId(Long userId, Pageable pageable) {
        log.info("finding following users from user service, userId: {}", userId);
        List<UserResponse> followingUsers = userClient.findFollowingUsers(userId).getData();
        if(followingUsers == null || followingUsers.size() == 0){
            return new ArrayList<PostResponse>();
        }

        List<Long> ids = new ArrayList<>();
        for(UserResponse userResponse : followingUsers) {
            ids.add(userResponse.getId());
        }

        log.info("finding following users' posts");
        List<Post> posts =  postRepository.findByUserIdInAndState(ids, 0, pageable);
        return posts.stream().map(post -> getPostResponse(post)).toList();
    }



    @Override
    public int upvote(Long userId, String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        Upvote upvote = upvoteRepository.findUpvoteByUserIdAndPost(userId, post);
        if(upvote != null)
            throw new ApiRequestException("You've already upvote this post");
        upvote = new Upvote(userId, post);
        upvoteRepository.save(upvote);
        post.setUpvotes(post.getUpvotes()+1);
        postRepository.save(post);
        return post.getUpvotes();
    }

    @Override
    public int unUpvote(Long userId, String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        Upvote upvote = upvoteRepository.findUpvoteByUserIdAndPost(userId, post);
        if(upvote == null)
            throw new ApiRequestException("You didn't upvote this post yet!");
        upvoteRepository.delete(upvote);
        post.setUpvotes(post.getUpvotes()-1);
        postRepository.save(post);
        return post.getUpvotes();
    }

    @Override
    public PostResponse findById(String postId) {
        return postRepository.findById(postId)
                .map(post -> getPostResponse(post)).orElseThrow();
    }

    @Override
    public int downvote(Long userId, String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        Downvote downvote = downvoteRepository.findDownvoteByUserIdAndPost(userId, post);
        if(downvote != null)
            throw new ApiRequestException("You've already downvote this post");
        downvote = new Downvote(userId, post);
        downvoteRepository.save(downvote);
        post.setDownvotes(post.getDownvotes()+1);
        postRepository.save(post);
        return post.getDownvotes();
    }

    @Override
    public int unDownvote(Long userId, String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        Downvote downvote = downvoteRepository.findDownvoteByUserIdAndPost(userId, post);
        if(downvote == null)
            throw new ApiRequestException("You didn't downvote this post yet!");
        downvoteRepository.delete(downvote);
        post.setDownvotes(post.getDownvotes()-1);
        postRepository.save(post);
        return post.getDownvotes();
    }
}
