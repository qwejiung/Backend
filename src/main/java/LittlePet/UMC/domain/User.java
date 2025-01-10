package LittlePet.UMC.domain;

import LittlePet.UMC.domain.Auditing.BaseTimeEntity;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProvider;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//유저 이름,성별,소셜로그인 등
@Getter @Setter @ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "Phone", nullable = false, length = 10)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String email;

    private String password;

    private String introduction;

    private String profilePhoto;

    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_provider")
    private SocialProvider socialProvider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleStatus role;

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
