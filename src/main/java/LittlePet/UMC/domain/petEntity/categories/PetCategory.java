package LittlePet.UMC.domain.petEntity.categories;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @ToString.Exclude
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

    public static String returnSpeciesPetCategoryComment(Comment comment){

        if (comment.getUser().getUserPetList() == null || comment.getUser().getUserPetList().isEmpty()) {
            return null;
        }
        return comment.getUser().getUserPetList().get(0).getPetCategory().getSpecies();
    }


    public static String returnSpeciesPetCategoryPost(Post post){

        if (post.getUser().getUserPetList() == null || post.getUser().getUserPetList().isEmpty()) {
            return null;
        }

        for (UserPet userPet : post.getUser().getUserPetList()) {
            if(userPet.getPetCategory().getSpecies().equals(post.getPetCategory().getSpecies()))
                return userPet.getPetCategory().getSpecies();
        }
        return null;
    }
}
