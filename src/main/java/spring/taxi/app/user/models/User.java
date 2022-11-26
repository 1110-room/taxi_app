package spring.taxi.app.user.models;

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

    // переделать
    private Role role = Role.USER;

    private boolean ready = false;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;


//    private boolean isOwner;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @OneToOne
    @JoinColumn(name = "owner_ride_id")
    private Ride ownersRide;

    public boolean isOwner(){
        return ride.getOwner().equals(this);
    }
}
