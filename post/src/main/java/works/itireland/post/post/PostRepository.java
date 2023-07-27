package works.itireland.post.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserId(Long userId, Pageable pageable);

    @Query("from Post p where p.userId in ids")
    List<Post> findByUserIds(List<Long> ids, Pageable pageable);
}
