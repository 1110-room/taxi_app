package spring.taxi.app.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.taxi.app.ride.models.Ride;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String surname;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    private boolean ready = false;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @OneToOne(mappedBy = "owner")
    @Transient
    private Ride ownersRide;

    public boolean isOwner(){
        return ride.getOwner().equals(this);
    }

    public User(String name, String surname, Role role, boolean ready, List<Review> reviews, Ride ride, Ride ownersRide) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.ready = ready;
        this.reviews = reviews;
        this.ride = ride;
        this.ownersRide = ownersRide;
    }
}
