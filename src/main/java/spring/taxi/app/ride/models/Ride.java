package spring.taxi.app.ride.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


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

//    @OneToMany(mappedBy = "user")
//    private List<User> members;

//    @OneToOne(mappedBy = "ride")
//    private User owner;
}
