package LittlePet.UMC.domain.petEntity.mapping;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//유저가 등록한 소동물들 - 처음 등록할때 종,체중,프로필사진 등
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class UserPet extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String profilePhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  //소분류 카테고리로 바꿔도 됨
    @JoinColumn(name = "pet_category_id",nullable = false)
    private PetCategory petCategory;

    @OneToMany(mappedBy = "userPet", cascade = CascadeType.ALL)
    private List<HealthRecord> healthRecordList= new ArrayList<>();

    public void updatePetInfo(String name, LocalDate birthDay, Gender gender) {
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public static String returnGenderPetCategoryPost(Post post){

        if (post.getUser().getUserPetList() == null || post.getUser().getUserPetList().isEmpty()) {
            return null;
        }

        for (UserPet userPet : post.getUser().getUserPetList()) {
            if(userPet.getPetCategory().getSpecies().equals(post.getPetCategory().getSpecies()))
                return userPet.getGender().toString();
        }
        return null;
    }

    public static String returnGenderPetCategoryComment(Comment comment){

        if (comment.getUser().getUserPetList() == null || comment.getUser().getUserPetList().isEmpty()) {
            return null;
        }
        return comment.getUser().getUserPetList().get(0).getGender().toString();
    }
}

