package LittlePet.UMC.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//소동물 정보 - 먹이정보,생활환경,필수템 등
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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


    private String mustHaveItemFood;


    private String mustHaveItemToys;

    private String mustHaveItemCage;

    private String featureImagePath;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SmallCategory_Id")
    private PetSmallCategory smallcategory;

    // Getters and Setters
}