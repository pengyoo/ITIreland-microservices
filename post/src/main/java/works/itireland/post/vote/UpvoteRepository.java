package works.itireland.post.vote;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import works.itireland.post.post.Post;

/**
 *
 */

@Repository
public interface UpvoteRepository extends MongoRepository<Upvote, String> {
    Upvote findUpvoteByUserIdAndPost(Long userId, Post post);
}
