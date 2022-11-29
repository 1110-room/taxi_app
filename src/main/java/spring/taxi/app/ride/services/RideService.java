package spring.taxi.app.ride.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.taxi.app.ride.models.Ride;
import spring.taxi.app.ride.models.RideStatus;
import spring.taxi.app.ride.repos.RideRepo;
import spring.taxi.app.ride.util.TaxiModel;
import spring.taxi.app.ride.util.TaxiParser;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.services.UserService;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepo rideRepo;
    private final UserService userService;

    public Ride findById(long id) {
        return rideRepo.findById(id).orElse(null);
    }

    public String changeStatus(Ride reqRide) {
        Ride ride = findById(reqRide.getId());
        if (ride != null) {
            ride.setStatus(reqRide.getStatus());
            if (ride.getStatus() == RideStatus.FULL
                    && !ride.getAddressFrom().isEmpty()
                    && !ride.getAddressTo().isEmpty())
            {
                TaxiModel taxiData;
                try {
                    taxiData = TaxiParser.getInfo();
                    ride.setCost((int) taxiData.getEconom().getPrice());
                } catch (IOException e) {
                    return "Error getting taxi price";
                }
                rideRepo.save(ride);
            }
            return "";
        }
        return "Ride with ride_id = %d doesn't exists".formatted(reqRide.getId());
    }

    public String changeRideOwner(Map<String, Object> body) {
        long rideId, ownerId;

        try {
            rideId = ((Number) body.get("ride_id")).longValue();
        } catch (ClassCastException classCastException) {
            return "Incorrect ride_id";
        }
        try {
            ownerId = ((Number) body.get("owner_id")).longValue();
        } catch (ClassCastException classCastException) {
            return "Incorrect owner_id";
        }

        Ride ride = findById(rideId);
        if (ride == null) {
            return "Ride with ride_id = %d doesn't exists".formatted(rideId);
        }
        User owner = userService.getById(ownerId);
        if (owner == null) {
            return "User with owner_id = %d doesn't exists".formatted(ownerId);
        }

        ride.setOwner(owner);
        rideRepo.save(ride);
        return "";
    }
}
