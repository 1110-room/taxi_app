package spring.taxi.app.ride.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.taxi.app.ride.models.Ride;

@Repository
public interface RideRepo extends JpaRepository<Ride, Long> {

}
