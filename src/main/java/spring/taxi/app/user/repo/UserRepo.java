package spring.taxi.app.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.taxi.app.ride.models.Ride;
import spring.taxi.app.user.models.Review;
import spring.taxi.app.user.models.User;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
