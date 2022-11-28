package spring.taxi.app.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.taxi.app.user.models.Review;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.services.ReviewService;
import spring.taxi.app.user.services.UserService;
import spring.taxi.app.user.util.ErrorResponse;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public User getById(@PathVariable long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@RequestBody @Valid User updUser, BindingResult bindingResult,
                                    @PathVariable long id) {

        List<String> errors = userService.validate(updUser);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(errors, LocalDateTime.now())
            );
        }
        userService.update(updUser, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/leaved-reviews")
    public List<Review> leavedReviews(@PathVariable long id) {
        return userService.getLeavedReviews(id);
    }

    @GetMapping("/{id}/received-reviews")
    public List<Review> receivedReviews(@PathVariable long id) {
        return userService.getReceivedReviews(id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody @Valid User user) {
        List<String> errors = userService.validate(user);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(errors, LocalDateTime.now())
            );
        }
        userService.create(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave-review")
    public ResponseEntity<?> leaveReview(@RequestBody Review review) {

        List<String> errors = reviewService.add(review);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(errors, LocalDateTime.now())
            );
        }
        return ResponseEntity.ok().build();
    }
}
