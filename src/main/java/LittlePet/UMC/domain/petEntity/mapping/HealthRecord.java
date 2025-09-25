package LittlePet.UMC.domain.petEntity.mapping;
import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.enums.*;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class HealthRecord extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MealAmountEnum mealAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FecesStatusEnum fecesStatus;

    
    @Enumerated(EnumType.STRING)
    private FecesColorStatusEnum fecesColorStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HealthStatusEnum healthStatus;


    @Enumerated(EnumType.STRING)
    private AtypicalSymptomEnum atypicalSymptom;

    @Column(nullable = true)
    private String otherSymptom; // "기타" 증상 필드 추가

    private String diagnosisName;

    private String prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id",nullable = false)
    private UserPet userPet;

    // Getters, Setters, Constructors
}
