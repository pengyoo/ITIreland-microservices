package works.itireland;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import works.itireland.domain.Post;


@Repository
public interface PostRepository extends MongoRepository<Post, Long> {
}
