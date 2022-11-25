package spring.taxi.app.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.repo.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getById(long id){
        return userRepo.findById(id).orElse(null);
    }

    @Transactional
    public void update(User updUser, long id){
        User user = this.getById(id);
        if (updUser.getName() != null)
            user.setName(updUser.getName());
        if (updUser.getSurname() != null)
            user.setSurname(user.getSurname());
        if (updUser.getReviews() != null)
            user.setReviews(updUser.getReviews());
        if (updUser.getRole() != null)
            user.setRole(user.getRole());
        userRepo.save(user);
    }
}
