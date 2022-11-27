package spring.taxi.app.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.taxi.app.user.models.Review;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getById(@PathVariable long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<HttpStatus> update(@RequestBody User updUser, @PathVariable long id) {
        if (userService.getById(id) != null) {
            userService.update(updUser, id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/leaved_reviews")
    public List<Review> leavedReviews(@PathVariable long id) {
        return userService.getLeavedReviews(id);
    }

    @GetMapping("/{id}/received_reviews")
    public List<Review> receivedReviews(@PathVariable long id) {
        return userService.getReceivedReviews(id);
    }


}
