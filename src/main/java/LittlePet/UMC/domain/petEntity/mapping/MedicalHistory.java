//package LittlePet.UMC.domain.petEntity.mapping;
//
//import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Getter @Setter
//@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Entity
//@ToString
//public class MedicalHistory extends BaseTimeEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String diagnosisName;
//
//    @Column(nullable = false)
//    private String prescription;
//
////    private String specialCare;
////    private String affectedArea;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "pet_Id",nullable = false)
////    private UserPet userPet;
////
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "hospital_id",nullable = false)
////    private Hospital hospital;
//
//}
