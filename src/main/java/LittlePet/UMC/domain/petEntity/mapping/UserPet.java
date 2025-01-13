package LittlePet.UMC.domain.petEntity.mapping;

import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.hospitalEntity.mapping.MedicalHistory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//유저가 등록한 소동물들 - 처음 등록할때 종,체중,프로필사진 등
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class UserPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private int age;

    private String profilePhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  //소분류 카테고리로 바꿔도 됨
    @JoinColumn(name = "pet_category_id",nullable = false)
    private PetCategory petCategory;

    @OneToMany(mappedBy = "userPet", cascade = CascadeType.ALL)
    private List<MedicalHistory> medicalHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "userPet", cascade = CascadeType.ALL)
    private List<HealthRecord> healthRecordList= new ArrayList<>();
}

