package LittlePet.UMC.domain.userEntity;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import LittlePet.UMC.domain.hospitalEntity.mapping.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//유저 이름,성별,소셜로그인 등
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
@Table(name = "`user`")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
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
    private List<HealthRecord> healthrecordList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostLike> postlikeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<HospitalPref> hospitalprefList = new ArrayList<>();
    // Getters and Setters
}
