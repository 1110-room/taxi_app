package spring.taxi.app.ride.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.taxi.app.ride.models.Ride;
import spring.taxi.app.ride.repos.RideRepo;
import spring.taxi.app.user.services.UserService;

import java.util.List;


@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {
    private final RideRepo rideRepo;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createRide(@RequestBody Ride ride) {
        rideRepo.save(ride);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-status")
    public ResponseEntity<?> changeStatus(@RequestBody Ride reqRide) {
        Ride ride = rideRepo.findById(reqRide.getId()).orElse(null);
        if (ride != null) {
            ride.setStatus(reqRide.getStatus());
            rideRepo.save(ride);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/history")
    public List<Ride> userRideHistory(@RequestParam("user_id") long userId) {
//        List<Ride> rideHistory = userService.getRideHistory(userId);
//        if (rideHistory == null) {
//            return ResponseEntity.badRequest().body("User doesn't exists!");
//        }
        return rideRepo.getUserRideHistory(userId);
    }
}
