package spring.taxi.app.ride.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.taxi.app.ride.models.Ride;
import spring.taxi.app.ride.models.RideStatus;
import spring.taxi.app.ride.repos.RideRepo;
import spring.taxi.app.ride.util.TaxiModel;
import spring.taxi.app.ride.util.TaxiParser;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.services.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
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
                    && !ride.getAddressTo().isEmpty()
            ) {
                // get coords by address from/to
                double[][] coords = {{49.1843750053, 55.7448325943}, {49.1148428819, 55.7937841116}};
                try {
                    TaxiModel taxiData = TaxiParser.getOptimalServiceInfo(coords);
                    updateRide(ride, taxiData);
                } catch (IOException e) {
                    return "Error getting taxi price";
                }
            }
            if (ride.getStatus() == RideStatus.FINISH && ride.getDtFrom() != null) {
                ride.setDtTo(new Date());
            }
            rideRepo.save(ride);
            return "";
        }
        return "Ride with ride_id = %d doesn't exists".formatted(reqRide.getId());
    }

    private void updateRide(Ride ride, TaxiModel taxiData) {
        ride.setPrice((int) taxiData.getEconom().getPrice());
        ride.setDistance(taxiData.getEconom().getDistance());
        ride.setTaxiSerivce(taxiData.getSerivce());
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
