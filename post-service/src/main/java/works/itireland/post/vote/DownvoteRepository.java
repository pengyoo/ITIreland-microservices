package works.itireland.post.vote;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import works.itireland.post.post.Post;

/**
 *
 */

@Repository
public interface DownvoteRepository extends MongoRepository<Downvote, String> {
    Downvote findDownvoteByUserIdAndPost(Long userId, Post post);
}
