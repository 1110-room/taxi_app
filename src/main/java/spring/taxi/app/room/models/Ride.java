package spring.taxi.app.room.models;

import org.springframework.data.jpa.repository.Query;
import spring.taxi.app.user.models.User;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "rooms")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int cost;

    private String from;
    private String to;

    @Enumerated(value = EnumType.STRING)
    private RideStatus status;

    @OneToMany(mappedBy = "ride")
    private List<User> members;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

}
