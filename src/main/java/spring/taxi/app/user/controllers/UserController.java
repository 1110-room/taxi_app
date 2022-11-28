package spring.taxi.app.user.controllers;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @GetMapping("/{id}/leaved_reviews")
    public List<Review> leavedReviews(@PathVariable long id) {
        return userService.getLeavedReviews(id);
    }

    @GetMapping("/{id}/received_reviews")
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
}
