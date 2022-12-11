package spring.taxi.app.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.taxi.app.user.models.Review;
import spring.taxi.app.user.models.User;
import spring.taxi.app.user.repo.ReviewRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final UserService userService;

    @Transactional
    public List<String> add(Review review) {
        List<String> errors = new ArrayList<>();

        User leaving = review.getLeavingUser();
        User receiving = review.getReceivingUser();

        if (userService.findById(leaving.getId()) == null){
            errors.add("Leaving user not found");
        }

        if (userService.findById(receiving.getId()) == null){
            errors.add("Receiving user not found");
        }

        if (errors.isEmpty()){
            Review review1 = new Review(review.getScore(),
                    leaving,
                    receiving);

            reviewRepo.save(review1);
        }

        return errors;
    }
}
