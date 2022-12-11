package spring.taxi.app.ride.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import spring.taxi.app.ride.util.taxi.TaxiSerivce;
import spring.taxi.app.user.models.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    private int price;

    @JsonProperty(value = "ride_size")
    @Column(name = "ride_size")
    private int rideSize;

    private float distance;

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "taxi_service")
    @Column(name = "taxi_service")
    private TaxiSerivce taxiSerivce;

    @Column(name = "address_from")
    @JsonProperty("address_from")
    private String addressFrom;

    @Column(name = "address_to")
    @JsonProperty("address_to")
    private String addressTo;

    @Column(name = "dt_from")
    @JsonProperty("dt_from")
    private Date dtFrom;

    @Column(name = "dt_to")
    @JsonProperty("dt_to")
    private Date dtTo;

    @Enumerated(value = EnumType.STRING)
    private RideStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ride_user",
            joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIncludeProperties(value = {"id", "name", "surname", "avatar"})
    private List<User> members;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonIncludeProperties(value = {"id", "name", "surname", "avatar"})
    private User owner;

    public Ride(User owner, int rideSize, String addressFrom, String addressTo, TaxiSerivce taxiSerivce) {
        this.owner = owner;
        this.rideSize = rideSize;
        this.taxiSerivce = taxiSerivce;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;

        this.price = 0;
        this.distance = 0;
        this.dtFrom = new Date();
        this.status = RideStatus.OPEN;
    }

    public Ride(int price, int rideSize, float distance, TaxiSerivce taxiSerivce, String addressFrom, String addressTo, Date dtFrom, Date dtTo, RideStatus status, List<User> members, User owner) {
        this.price = price;
        this.rideSize = rideSize;
        this.distance = distance;
        this.taxiSerivce = taxiSerivce;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.dtFrom = dtFrom;
        this.dtTo = dtTo;
        this.status = status;
        this.members = members;
        this.owner = owner;
    }

    @JsonIgnore
    public int getRideTimeMinutes() {
        if (dtTo != null && dtFrom != null) {
            return (int) ((dtTo.getTime() - dtFrom.getTime()) / 1000 / 60);
        }
        return 0;
    }
}
