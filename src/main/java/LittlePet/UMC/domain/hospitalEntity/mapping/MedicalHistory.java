package LittlePet.UMC.domain.hospitalEntity.mapping;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class MedicalHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialCare;
    private String diagnosisName;
    private String prescription;
    private String affectedArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_Id",nullable = false)
    private UserPet userPet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id",nullable = false)
    private Hospital hospital;

}
