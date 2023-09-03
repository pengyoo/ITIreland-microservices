package works.itireland.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import works.itireland.user.domain.Following;
import works.itireland.user.domain.User;

import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    @Query("SELECT f.follower FROM Following f WHERE f.following = :user")
    Page<User> findFollowersByUser(@Param("user")User user, Pageable pageable);

    @Query("SELECT f.following FROM Following f WHERE f.follower = :user")
    Page<User> findFollowingsByUser(@Param("user") User user, Pageable pageable);

    Optional<Following> findByFollowingAndFollower(User followingUser, User followerUser);

}
