package spring.taxi.app.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.taxi.app.user.models.Review;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.repo.ReviewRepo;
import spring.taxi.app.user.repo.UserRepo;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final ReviewRepo reviewRepo;


    public User getById(long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User findByVkId(long vkId) {
        return userRepo.findByVkId(vkId).orElse(null);
    }

    public boolean registerUser(Map<String, Object> userAttributes) {
        try {
            long vkId = ((Number) userAttributes.get("id")).longValue();
            User user = findByVkId(vkId);
            if (user == null) {
                String name = (String) userAttributes.get("first_name");
                String surname = (String) userAttributes.get("last_name");

                URL url = new URL(userAttributes.get("photo_max").toString());
                InputStream in = new BufferedInputStream(url.openStream());
                byte[] avatar = in.readAllBytes();
                in.close();

                user = new User(vkId, name, surname, avatar);
                userRepo.save(user);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public void update(User updUser, long id) {
        User user = this.getById(id);
        if (updUser.getName() != null)
            user.setName(updUser.getName());
        if (updUser.getSurname() != null)
            user.setSurname(user.getSurname());
        if (updUser.getRole() != null)
            user.setRole(user.getRole());
        if (updUser.getCardNumber() != null)
            user.setCardNumber(updUser.getCardNumber());
        userRepo.save(user);
    }

    public List<Review> getLeavedReviews(long id) {
        return reviewRepo.findAllByLeavingUserId(id);
    }

    public List<Review> getReceivedReviews(long id) {
        return reviewRepo.findAllByReceivingUserId(id);
    }

    public List<String> validate(User user){
        List<String> errors = new ArrayList<>();
        if (user.getName().isEmpty())
            errors.add("Name is empty");
        if (user.getSurname().isEmpty())
            errors.add("Surname is empty");
        if (user.getCardNumber().length() != 16 || user.getCardNumber() == null)
            errors.add("Invalid card number");
        else if (userRepo.findByCardNumber(user.getCardNumber()).isPresent()){
            errors.add("Recurring card");
        }
        return errors;
    }

    @Transactional
    public void create(User user){
        userRepo.save(user);
    }
}
