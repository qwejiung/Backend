package LittlePet.UMC.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class HospitalPref {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long placeId;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    // Getters, Setters, Constructors
}

