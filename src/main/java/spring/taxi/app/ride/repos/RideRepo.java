package spring.taxi.app.ride.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.taxi.app.ride.models.Ride;

import java.util.List;

@Repository
public interface RideRepo extends JpaRepository<Ride, Long> {

    @Query(value = "select r.* from rides r inner join ride_user ru on r.id = ru.ride_id where ru.user_id = :user_id",
            nativeQuery = true)
    List<Ride> getUserRideHistory(@Param("user_id") long userId);

    @Query(value = "select r from Ride r where r.status = 'OPEN' order by r.dtFrom desc")
    List<Ride> getOpenRides();

}
