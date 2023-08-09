package works.itireland.post.comment;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import works.itireland.clients.post.CommentRequest;
import works.itireland.clients.post.CommentResponse;
import works.itireland.clients.user.UserClient;
import works.itireland.clients.user.UserResponse;
import works.itireland.exception.ApiRequestException;
import works.itireland.post.post.Post;
import works.itireland.post.post.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserClient userClient;

    @Override
    public CommentResponse save(CommentRequest commentRequest) {
        Comment comment = getComment(commentRequest);
        comment.setCtime(LocalDateTime.now());
        comment.setUtime(LocalDateTime.now());
        comment = commentRepository.save(comment);
        return getCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> findAllByPostId(String postId, Pageable pageable) {
        // TODO: fix bug, can't fetch sub-comments
        return commentRepository.findAllByPostIdAndState(postId, 0, pageable)
                .stream()
                .map( comment -> getCommentResponse(comment))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if(comment == null || comment.getUserId() != userId) {
            throw new ApiRequestException("Comment is not your comment or not exist.");
        }
        comment.setState(-1);
        commentRepository.save(comment);
    }

    private Comment getComment(CommentRequest commentRequest){

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentRequest, comment);

        // Process Post
        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow();
        comment.setPost(post);

        // Process parent comment
        if(commentRequest.getParentId() != null){
            Comment parentComment = commentRepository.findById(commentRequest.getParentId()).orElseThrow();
            comment.setParentComment(parentComment);

        }
        return comment;
    }

    private CommentResponse getCommentResponse(Comment comment){
        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(comment, commentResponse);

        // Process postId
        commentResponse.setPostId(comment.getPost().getId());

        // Process user
        UserResponse userResponse = userClient.find(comment.getUserId()).getData();
        commentResponse.setUser(userResponse);

        return processChildrenComments(comment, commentResponse);
    }

    /**
     * Recursively process children comments
     * @param comment
     * @return
     */
    private CommentResponse processChildrenComments(Comment comment, CommentResponse commentResponse){

        if(comment.getComments() == null || comment.getComments().isEmpty()) {
            return commentResponse;
        }

        List<CommentResponse> list = new ArrayList<>();
        BeanUtils.copyProperties(comment, commentResponse);
        for(Comment c : comment.getComments()){
            CommentResponse cr = new CommentResponse();
            UserResponse userResponse = userClient.find(c.getUserId()).getData();
            cr.setUser(userResponse);
            cr.setPostId(c.getPost().getId());
            list.add(processChildrenComments(c, cr));
        }
        commentResponse.setChildrenComments(list);
        return commentResponse;
    }
}
