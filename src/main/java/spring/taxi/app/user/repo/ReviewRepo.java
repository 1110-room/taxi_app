package spring.taxi.app.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.taxi.app.user.models.Review;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findAllByLeavingUserId(long id);

    List<Review> findAllByReceivingUserId(long id);
}
