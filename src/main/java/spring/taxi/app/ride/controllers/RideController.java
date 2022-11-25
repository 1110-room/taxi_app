package spring.taxi.app.ride.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.taxi.app.ride.models.Ride;
import spring.taxi.app.ride.repos.RideRepo;

import java.util.Optional;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {
    private final RideRepo rideRepo;

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
}
