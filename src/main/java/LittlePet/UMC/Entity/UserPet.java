package LittlePet.UMC.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//유저가 등록한 소동물들 - 처음 등록할때 종,체중,프로필사진 등
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class UserPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "BirthDay")
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "Gender")
    private Gender gender;

    @Column(name = "Weight")
    private BigDecimal weight;

    @Column(name = "ProfilePhoto")
    private String profilePhoto;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  //소분류 카테고리로 바꿔도 됨
    @JoinColumn(name = "smallanimal_Id")
    private Petinfo petinfo;

    @OneToMany(mappedBy = "userpet", cascade = CascadeType.ALL)
    private List<HospitalPet> hospitalpetList = new ArrayList<>();

    @OneToMany(mappedBy = "userpet", cascade = CascadeType.ALL)
    private List<HealthRecord> healthrecordList= new ArrayList<>();


}

