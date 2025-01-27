package LittlePet.UMC.domain.petEntity.categories;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
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

    private String features;

    private String foodInfo;

    private String environment;

    private String playMethods;

    @Column(nullable = false)
    private String featureImagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_big_category_id",nullable = false)
    private PetBigCategory petBigCategory;

    // Getters and Setters
}
