package spring.taxi.app.ride.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.taxi.app.ride.models.Ride;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RideRepo extends JpaRepository<Ride, Long> {
    @Transactional
    @Query(value = """
            select r.*
            from (
                select r.*
                from rides r
                    inner join ride_user ru
                    on r.id = ru.ride_id
                where ru.user_id = :user_id
                union
                select r.*
                from rides r
                where r.owner_id = :user_id
            ) r
            where r.status in ('MOVE', 'FINISH')
            """, nativeQuery = true)
    List<Ride> getUserRideHistory(@Param("user_id") long userId);

    @Transactional
    @Query(value = "select r from Ride r where r.status = 'OPEN' order by r.dtFrom desc")
    List<Ride> getOpenRides();

}
