package spring.taxi.app.ride.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
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
    private Date dateFrom = new Date();

    @Column(name = "date_to")
    @JsonProperty("date_to")
    @DateTimeFormat
    private Date dateTo;

    @Enumerated(value = EnumType.STRING)
    private RideStatus status = RideStatus.OPEN;

    @OneToMany(mappedBy = "ride")
    private List<User> members;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

}
