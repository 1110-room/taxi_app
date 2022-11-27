package spring.taxi.app.user.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.taxi.app.ride.models.Ride;

import javax.persistence.*;
import javax.swing.text.View;
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

    private String name;

    private String surname;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    private boolean ready = false;

    @OneToMany(mappedBy = "receivingUser")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Review> receivedReviews;

    @OneToMany(mappedBy = "leavingUser")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Review> leavedReviews;

    @ManyToMany(mappedBy = "members")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Ride> ride;

    @OneToOne(mappedBy = "owner")
    @Transient
    private Ride ownersRide;

    public boolean isOwner() {
        return ownersRide == null;
    }

    public User(String name, String surname, Role role, boolean ready, List<Ride> ride, Ride ownersRide) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.ready = ready;
//        this.reviews = reviews;
        this.ride = ride;
        this.ownersRide = ownersRide;
    }
}
