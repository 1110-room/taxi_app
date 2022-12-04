package spring.taxi.app.ride.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.taxi.app.ride.models.Ride;
import spring.taxi.app.ride.repos.RideRepo;
import spring.taxi.app.ride.services.RideService;
import spring.taxi.app.user.services.UserService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {
    private final RideRepo rideRepo;
    private final RideService rideService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createRide(@RequestBody Ride ride) {
        rideRepo.save(ride);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-status")
    public ResponseEntity<?> changeStatus(@RequestBody Ride reqRide) {
        String result = rideService.changeStatus(reqRide);
        if (!result.isEmpty()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/set-owner")
    public ResponseEntity<?> setRideOwner(@RequestBody Map<String, Object> body) {

        String result = rideService.changeRideOwner(body);
        if (!result.isEmpty()) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public List<Ride> userRideHistory(@RequestParam("user_id") long userId) {
        return rideRepo.getUserRideHistory(userId);
    }

    @GetMapping("/open-line")
    public List<Ride> getOpenRidesLine() {
        return rideRepo.getOpenRides();
    }
}
