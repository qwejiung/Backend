package LittlePet.UMC.domain.petEntity.mapping;
import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.enums.FecesStatusEnum;
import LittlePet.UMC.domain.enums.HealthStatusEnum;
import LittlePet.UMC.domain.enums.MealAmountEnum;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HealthStatusEnum healthStatus;

    private String abnormalSymptoms;
    private String diagnosisName;
    private String prescription;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id",nullable = false)
    private UserPet userPet;

    //User가 꼭 필요할까 user Pet 내에도 user_id가 있긴 해
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters, Setters, Constructors
}
