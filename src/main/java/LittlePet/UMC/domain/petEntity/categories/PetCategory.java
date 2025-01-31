package LittlePet.UMC.domain.petEntity.categories;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class PetCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String species;

    //특징
    private String features;
    private String featuresHeadLine;

    //먹이정보
    private String foodInfo;
    private String foodInfoHeadLine;

    //환경
    private String environment;
    private String environmentHeadLine;

    //놀이방법
    private String playMethods;
    private String playMethodsHeadLine;

    @Column(nullable = false)
    private String featureImagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_big_category_id",nullable = false)
    private PetBigCategory petBigCategory;

    // Getters and Setters
}
