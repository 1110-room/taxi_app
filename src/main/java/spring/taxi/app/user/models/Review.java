package spring.taxi.app.user.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
