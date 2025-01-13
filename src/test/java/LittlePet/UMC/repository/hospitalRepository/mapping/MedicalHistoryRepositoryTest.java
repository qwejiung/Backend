//package LittlePet.UMC.repository.hospitalRepository.mapping;
//
//import LittlePet.UMC.UmcApplication;
//import LittlePet.UMC.domain.hospitalEntity.Hospital;
//import LittlePet.UMC.domain.hospitalEntity.mapping.MedicalHistory;
//import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
//import LittlePet.UMC.domain.petEntity.categories.PetCategory;
//import LittlePet.UMC.domain.petEntity.mapping.UserPet;
//import LittlePet.UMC.domain.userEntity.User;
//import LittlePet.UMC.repository.CreateEntity;
//import LittlePet.UMC.repository.UserRepository;
//import LittlePet.UMC.repository.hospitalRepository.HospitalRepository;
//import LittlePet.UMC.repository.petRepository.categories.PetBigCategoryRepository;
//import LittlePet.UMC.repository.petRepository.categories.PetCategoryRepository;
//import LittlePet.UMC.repository.petRepository.mapping.UserPetRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestPropertySource(locations = "classpath:application-test.properties")
//@SpringBootTest(classes = UmcApplication.class)
//public class MedicalHistoryRepositoryTest {
//
//    //레포지토리들
//    @Autowired private MedicalHistoryRepository medicalHistoryRepository;
//    @Autowired private UserRepository userRepository;
//    @Autowired private HospitalRepository hospitalRepository;
//    @Autowired private PetBigCategoryRepository petBigCategoryRepository;
//    @Autowired private PetCategoryRepository petCategoryRepository;
//    @Autowired private UserPetRepository userPetRepository;
//
//    //테스트
//    @Test
//    @DisplayName("MedicalHistory Entity Create Test")
//    public void MedicalHistoryEntityCreateTset() {
//        //외래키 엔터티 생성 및 저장
//        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
//        PetCategory petCategory = CreateEntity.createPetCategory(petBigCategory);
//        User user = CreateEntity.createUser();
//        UserPet userpet = CreateEntity.createUserPet(petCategory,user);
//        Hospital hospital = CreateEntity.createHospital();
//
//        userRepository.save(user);
//        hospitalRepository.save(hospital);
//        petBigCategoryRepository.save(petBigCategory);
//        petCategoryRepository.save(petCategory);
//        userPetRepository.save(userpet);
//
//        MedicalHistory medicalHistory = MedicalHistory.builder()
//                .specialCare("약먹고 당분간 휴식")
//                .diagnosisName("감기")
//                .prescription("감기약 3일분")
//                .affectedArea("면역력 저하,기관지")
//                .userPet(userpet)
//                .hospital(hospital)
//                .build();
//        MedicalHistory savedmedicalHistory = medicalHistoryRepository.save(medicalHistory);
//        System.out.println("MedicalHistory"+savedmedicalHistory);
//
//    }
//
//
//
//
//}