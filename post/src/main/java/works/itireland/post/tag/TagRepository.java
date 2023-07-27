package works.itireland.post.tag;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import works.itireland.post.tag.Tag;


@Repository
public interface TagRepository extends MongoRepository<Tag, String> {
}
