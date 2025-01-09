package LittlePet.UMC.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class HospitalPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialCare;
    private String diagnosisName;
    private String prescription;
    private String affectedArea;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_Id")
    private UserPet userpet;

    private String placeId;

    // Getters, Setters, Constructors
}
