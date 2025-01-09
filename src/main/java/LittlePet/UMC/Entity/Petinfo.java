package LittlePet.spring.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//소동물 정보 - 먹이정보,생활환경,필수템 등
@Entity
public class Petinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Features")
    private String features;

    @Column(name = "FoodInfo")
    private String foodInfo;

    @Column(name = "Environment") //생활환경
    private String environment;

    @Column(name = "PlayMethods") //놀이방법
    private String playMethods;

    @Column(name = "must_have_item_Food")
    private String mustHaveItemFood;

    @Column(name = "must_have_item_Toys")
    private String mustHaveItemToys;

    @Column(name = "must_have_item_Cage")
    private String mustHaveItemCage;

    @Column(name = "feature_image_path")
    private String featureImagePath;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SmallCategory_Id")
    private PetSmallCategory smallcategory;

    // Getters and Setters
}