package LittlePet.UMC.domain.hospitalEntity;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalStarRating;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class Hospital extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long placeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String closedDay;

//    private String operationAt;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<HospitalStarRating> hospitalStarRatingList= new ArrayList<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<HospitalPref> hospitalPrefList= new ArrayList<>();

//    @OneToMany(mappedBy = "hospital") //cascade 안 되도록
//    private List<MedicalHistory> medicalHistoryList = new ArrayList<>();

    // Getters, Setters, Constructors
}

