package spring.taxi.app.ride.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
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

    @Column(name = "address_from")
    @JsonProperty("address_from")
    private String addressFrom;

    @Column(name = "address_to")
    @JsonProperty("address_to")
    private String addressTo;

    @Column(name = "date_from")
    @JsonProperty("date_from")
    @DateTimeFormat
    private Date dtFrom = new Date();

    @Column(name = "date_to")
    @JsonProperty("date_to")
    @DateTimeFormat
    private Date dtTo;

    @Enumerated(value = EnumType.STRING)
    private RideStatus status = RideStatus.OPEN;

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

}
