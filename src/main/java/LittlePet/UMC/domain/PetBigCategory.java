package LittlePet.UMC.domain;

import LittlePet.UMC.domain.Auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//소동물 대분류 - 설치류 , 파충류
@Getter @Setter @ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class PetBigCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Enum으로 관리
    private String category_name;

    @OneToMany(mappedBy = "bigcategory", cascade = CascadeType.ALL)
    private List<PetSmallCategory> small_categories = new ArrayList<>();

    // Getters and Setters
}
