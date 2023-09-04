package works.itireland.post.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import works.itireland.post.post.Post;

public interface CommentRepository extends MongoRepository<Comment, String> {
    Page<Comment> findAllByPostIdAndState(String postId, int state, Pageable pageable);
    int countByPost(Post post);
}