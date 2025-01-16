package LittlePet.UMC.domain.petEntity.categories;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.postEntity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//소동물 대분류 - 설치류 , 파충류
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class PetBigCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "petBigCategory", cascade = CascadeType.ALL)
    private List<PetCategory> petCategoryList = new ArrayList<>();

    //꼭 필요할 것 같지는 않아서 일단 보류
//    @OneToMany(mappedBy = "petBigCategory", cascade = CascadeType.ALL)
//    private List<Post> postList= new ArrayList<>();

    // Getters and Setters
}
