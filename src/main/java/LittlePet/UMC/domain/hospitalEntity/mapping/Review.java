package LittlePet.UMC.domain.hospitalEntity.mapping;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.userEntity.User;
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
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false) //validation logic 필요함.
    private int rating;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender petGender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",nullable = false)
    private PetCategory petCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id",nullable = false)
    private Hospital hospital;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewMedia> reviewMediaList= new ArrayList<>();

    // Getters, Setters, Constructors
}
