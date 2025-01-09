package LittlePet.spring.Entity;

import jakarta.persistence.*;
import java.util.Date;
@Entity
public class HospitalPref {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long placeId;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

    // Getters, Setters, Constructors
}

