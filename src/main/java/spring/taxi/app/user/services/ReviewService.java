package spring.taxi.app.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.taxi.app.user.models.Review;
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
        if (review.getLeavingUser() == null) {
            if (userService.getById(review.getLeavingUser().getId()) == null) {
                errors.add("Вас не существует!!!!!!!");
            }
        } else {
            errors.add("Вас не существует!!!!!!!");
        }

        if (review.getReceivingUser() != null) {
            if (userService.getById(review.getReceivingUser().getId()) != null)
                reviewRepo.save(review);
        } else {
            errors.add("Выберите пользователя которому Вы хотите оставить отзыв");
        }

        return errors;
    }
}
