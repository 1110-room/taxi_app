package spring.taxi.app.room.models;

import javax.persistence.*;


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

//    @OneToMany(mappedBy = "user")
//    private List<User> members;

//    @OneToOne(mappedBy = "ride")
//    private User owner;
}
