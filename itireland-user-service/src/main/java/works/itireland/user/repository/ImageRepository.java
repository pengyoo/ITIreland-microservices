package works.itireland.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import works.itireland.user.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
