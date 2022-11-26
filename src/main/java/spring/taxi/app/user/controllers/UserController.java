package spring.taxi.app.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.services.UserService;

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
    public ResponseEntity<HttpStatus> update(@RequestBody User updUeser, @PathVariable long id){
        System.out.println(id);
        if (userService.getById(id) != null) {
            userService.update(updUeser, id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }


}
