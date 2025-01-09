package LittlePet.spring.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

//유저가 등록한 소동물들 - 처음 등록할때 종,체중,프로필사진 등
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  //소분류 카테고리로 바꿔도 됨
    @JoinColumn(name = "smallanimal_ID")
    private Petinfo petinfo;

    @OneToMany(mappedBy = "userpet", cascade = CascadeType.ALL)
    private List<HospitalPet> hospitalpetList = new ArrayList<>();

    @OneToMany(mappedBy = "userpet", cascade = CascadeType.ALL)
    private List<HealthRecord> healthrecordList= new ArrayList<>();


}

