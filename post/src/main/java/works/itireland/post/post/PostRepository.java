package works.itireland.post.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserIdAndState(Long userId, int state,Pageable pageable);

    List<Post> findByUserIdInAndState(List<Long> ids, int state, Pageable pageable);

    Page<Post> findByState(Pageable pageable, int state);

}
