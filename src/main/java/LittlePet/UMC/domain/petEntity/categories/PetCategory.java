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

    @Column(nullable = false, length = 50)
    private String species;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String features;

    @Column(columnDefinition = "TEXT")
    private String foodInfo;

    @Column(columnDefinition = "TEXT")
    private String environment;

    @Column(columnDefinition = "TEXT")
    private String playMethods;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String featureImagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_big_category_id",nullable = false)
    private PetBigCategory petBigCategory;

    // Getters and Setters

    public PetCategory(String species) {
        this.species = species;
    }

    public PetCategory(String species, String featureImagePath, PetBigCategory petBigCategory) {
        this.species = species;
        this.featureImagePath = featureImagePath;
        this.petBigCategory = petBigCategory;
    }
}
