package spring.taxi.app.ride.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.taxi.app.ride.models.Ride;
import spring.taxi.app.ride.repos.RideRepo;
import spring.taxi.app.ride.services.RideService;
import spring.taxi.app.ride.util.taxi.TaxiSerivce;
import spring.taxi.app.user.models.User;
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
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {
        Ride newRide = rideService.create(ride);
        if (newRide != null) {
            return ResponseEntity.ok().body(newRide);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public Ride getById(@PathVariable long id) {
        return rideService.findById(id);
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

    @GetMapping("/taxi-services")
    public List<TaxiSerivce> getTaxiServices() {
        return List.of(TaxiSerivce.values());
    }
}
