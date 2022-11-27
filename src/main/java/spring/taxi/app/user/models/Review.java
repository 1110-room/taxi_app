package spring.taxi.app.user.models;

import com.fasterxml.jackson.annotation.*;
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
    @JsonIgnore
    private long id;

    private int score;

    @ManyToOne()
    @JoinColumn(name = "leaving_user_id")
    @JsonIncludeProperties(value = {"id", "name", "surname"})
    private User leavingUser;

    @ManyToOne
    @JoinColumn(name = "receiving_user_id")
    @JsonIncludeProperties(value = {"id", "name", "surname"})
    private User receivingUser;

}
