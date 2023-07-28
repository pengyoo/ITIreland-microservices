package works.itireland.post.post;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 */

@Repository
public interface UpvoteRepository extends MongoRepository<Upvote, String> {
    Upvote findUpvoteByUserIdAndPost(Long userId, Post post);
}
