package spring.taxi.app.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.taxi.app.user.models.Review;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable long id){
        return userService.getById(id);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<HttpStatus> update(@RequestBody User updUser, @PathVariable long id){
        if (userService.getById(id) != null) {
            userService.update(updUser, id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/reviews")
    public List<Integer> reviews(@PathVariable long id){
        return userService.getReviews(id);
    }




}
