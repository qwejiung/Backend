package LittlePet.UMC.domain.userEntity;

import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;
import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import LittlePet.UMC.domain.postEntity.mapping.PostClipping;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalStarRating;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//유저 이름,성별,소셜로그인 등
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity

@Table(name = "`user`")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;


    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 30)
    private String email;

    @Column(nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialProviderEnum socialProvider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleStatus role;

    private String introduction;

    private String profilePhoto;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<HospitalStarRating> hospitalStarRatingList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostClipping> postClippingList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostLike> postlikeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<HospitalPref> hospitalprefList = new ArrayList<>();

    @PrePersist
    public void setDefaultRole() {
        if (this.role == null) {
            this.role = RoleStatus.USER; // 기본값 설정
        }
    }
    // Getters and Setters

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPet> userPetList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserBadge> userBadgeList= new ArrayList<>();


    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + name + "', email='" + email + "'}";
    }

    public void updateProfile(String name, String phone, String introduction) {
        this.name = name;
        this.phone = phone;
        this.introduction = introduction;

    }

    public User(String socialId, SocialProviderEnum socialProvider, RoleStatus role) {
        this.socialId = socialId;
        this.socialProvider = socialProvider;
        this.role = role;
    }
}
