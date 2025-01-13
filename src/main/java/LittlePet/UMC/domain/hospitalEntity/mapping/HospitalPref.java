package LittlePet.UMC.domain.hospitalEntity.mapping;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class HospitalPref extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id",nullable = false)
    private Hospital hospital;

    // Getters, Setters, Constructors
}

