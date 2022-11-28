package spring.taxi.app.user.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import spring.taxi.app.ride.models.Ride;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    private String surname;

    @Length(min = 16, max = 16, message = "The number must be 16 digits")
    private String cardNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    private boolean ready = false;

    @OneToMany(mappedBy = "receivingUser")
    private List<Review> receivedReviews;

    @OneToMany(mappedBy = "leavingUser")
    private List<Review> leavedReviews;

    @ManyToMany(mappedBy = "members")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Ride> ride;

    @OneToOne(mappedBy = "owner")
    @Transient
    private Ride ownersRide; // ссылка на поездку, если это владелец

    public boolean isOwner() {
        return ownersRide != null;
    }


    public User(String name, String surname, Role role, boolean ready, List<Review> receivedReviews,
                List<Review> leavedReviews, List<Ride> ride, Ride ownersRide, String cardNumber) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.ready = ready;
        this.receivedReviews = receivedReviews;
        this.leavedReviews = leavedReviews;
        this.ride = ride;
        this.ownersRide = ownersRide;
        this.cardNumber = cardNumber;
    }
}
