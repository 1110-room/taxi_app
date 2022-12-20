package spring.taxi.app.user.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import spring.taxi.app.ride.models.Ride;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
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

    private long vkId;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    private String surname;

    @Lob
    @Column(name = "avatar", columnDefinition = "BLOB")
    private byte[] avatar;

    @Column(name = "card_number")
    @JsonProperty(value = "card_number")
    @Length(min = 16, max = 16, message = "The number must be 16 digits")
    private String cardNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean ready;

    @Column(name = "ride_status")
    @JsonProperty(value = "ride_status")
    private int rideStatus;

    @JsonProperty(value = "received_reviews")
    @OneToMany(mappedBy = "receivingUser")
    private List<Review> receivedReviews;

    @JsonProperty(value = "leaved_reviews")
    @OneToMany(mappedBy = "leavingUser")
    private List<Review> leavedReviews;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    @JsonIncludeProperties(value = {"id"})
    private List<Ride> rides;

    @JsonIgnore
    @OneToOne(mappedBy = "owner")
    @Transient
    private Ride ownersRide; // ссылка на поездку, если это владелец

    @JsonIgnore
    public boolean isOwner() {
        return ownersRide != null;
    }

    public User(long vkId, String name, String surname, byte[] avatar) {
        this.vkId = vkId;
        this.name = name;
        this.surname = surname;
        this.avatar = avatar;

        this.role = Role.USER;
        this.ready = false;
        this.rideStatus = 0;
    }

    public User(long vkId, String name, String surname, byte[] avatar, String cardNumber, Role role, boolean ready, int rideStatus, List<Review> receivedReviews, List<Review> leavedReviews, List<Ride> rides, Ride ownersRide) {
        this.vkId = vkId;
        this.name = name;
        this.surname = surname;
        this.avatar = avatar;
        this.cardNumber = cardNumber;
        this.role = role;
        this.ready = ready;
        this.rideStatus = rideStatus;
        this.receivedReviews = receivedReviews;
        this.leavedReviews = leavedReviews;
        this.rides = rides;
        this.ownersRide = ownersRide;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", vkId=" + vkId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", avatar=" + Arrays.toString(avatar) +
                ", cardNumber='" + cardNumber + '\'' +
                ", role=" + role +
                ", ready=" + ready +
                ", rideStatus=" + rideStatus +
                ", receivedReviews=" + receivedReviews +
                ", leavedReviews=" + leavedReviews +
                ", rides=" + rides +
                ", ownersRide=" + ownersRide +
                '}';
    }
}
