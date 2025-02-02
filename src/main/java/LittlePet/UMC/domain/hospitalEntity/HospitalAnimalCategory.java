/*
package LittlePet.UMC.domain.hospitalEntity;

import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalAnimal;
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
public class HospitalAnimalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String animalType; // 예: "햄스터", "토끼", "다람쥐"

    @OneToMany(mappedBy = "hospitalAnimalCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HospitalAnimal> hospitalAnimalList = new ArrayList<>();
}
*/
