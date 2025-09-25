package LittlePet.UMC.domain.hospitalEntity;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
//import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalAnimal;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalStarRating;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class Hospital extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long placeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String closedDay;

    private String latitude;  // 위도
    private String longitude; // 경도

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private String openingHours;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = true)
    private String imageUrl;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<HospitalStarRating> hospitalStarRatingList= new ArrayList<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<HospitalPref> hospitalPrefList= new ArrayList<>();

    public void addHospitalPref(HospitalPref hospitalPref) {
        this.hospitalPrefList.add(hospitalPref);
    }

    public void updateCoordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateHospitalInfo(String openingHours, String phoneNumber, String imageUrl, Double rating) {
        this.openingHours = openingHours;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }


//    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<HospitalAnimal> hospitalAnimalList = new ArrayList<>();

//    @OneToMany(mappedBy = "hospital") //cascade 안 되도록
//    private List<MedicalHistory> megdicalHistoryList = new ArrayList<>();

    // Getters, Setters, Constructors
}

