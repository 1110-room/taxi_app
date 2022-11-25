package spring.taxi.app.user.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String role;

    private boolean ready = false;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;


}
