package spring.taxi.app.ride.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import spring.taxi.app.ride.util.taxi.TaxiSerivce;
import spring.taxi.app.user.models.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "rides")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int cost;

    private float distance;

    @Enumerated(EnumType.STRING)
    private TaxiSerivce taxiSerivce;

    @Column(name = "address_from")
    @JsonProperty("address_from")
    private String addressFrom;

    @Column(name = "address_to")
    @JsonProperty("address_to")
    private String addressTo;

    @Column(name = "date_from")
    @JsonProperty("date_from")
    @DateTimeFormat
    private Date dtFrom;

    @Column(name = "date_to")
    @JsonProperty("date_to")
    @DateTimeFormat
    private Date dtTo;

    @Enumerated(value = EnumType.STRING)
    private RideStatus status;

    @ManyToMany
    @JoinTable(
            name = "ride_user",
            joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIncludeProperties(value = {"id", "name", "surname"})
    private List<User> members;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonIncludeProperties(value = {"id", "name", "surname"})
    private User owner;

    public Ride(int cost, String addressFrom, Date dtFrom, RideStatus status) {
        this.cost = cost;
        this.addressFrom = addressFrom;
        this.dtFrom = dtFrom;
        this.status = status;
    }

    public Ride(int cost, String addressFrom, String addressTo, Date dtFrom, Date dtTo, RideStatus status, List<User> members, User owner) {
        this.cost = cost;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.dtFrom = dtFrom;
        this.dtTo = dtTo;
        this.status = status;
        this.members = members;
        this.owner = owner;
    }

    public int getRideTimeMinutes() {
        if (dtTo != null && dtFrom != null) {
            return (int) ((dtTo.getTime() - dtFrom.getTime()) / 1000 / 60);
        }
        return 0;
    }
}
