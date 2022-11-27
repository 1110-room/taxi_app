package spring.taxi.app.ride.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.taxi.app.ride.models.Ride;
import spring.taxi.app.user.models.User;

import java.util.List;

@Repository
public interface RideRepo extends JpaRepository<Ride, Long> {
    @Query(value = "select r from Ride r inner join User u on u.ride.id = r.id where u.id = :user_id")
    List<Ride> getUserRideHistory(@Param("user_id") long userId);

//    List<Ride> findByMembersContains(User user);
}
