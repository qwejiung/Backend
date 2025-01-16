package LittlePet.UMC.repository.hospitalRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalStarRating;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.CreateEntity;
import LittlePet.UMC.repository.UserRepository;
import LittlePet.UMC.repository.hospitalRepository.HospitalRepository;
import LittlePet.UMC.repository.petRepository.categories.PetBigCategoryRepository;
import LittlePet.UMC.repository.petRepository.categories.PetCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class ReviewRepositoryTest {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private HospitalRepository hospitalRepository;
    @Autowired private PetBigCategoryRepository petBigCategoryRepository;
    @Autowired private PetCategoryRepository petCategoryRepository;
    @Autowired private UserRepository userRepository;
    @Test
    @DisplayName("Review Entity Create Test")
    public void ReviewCreateTest() {
        //외래키 엔티티 생성 및 저장
        User user = CreateEntity.createUser();
        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
        PetCategory petCategory = CreateEntity.createPetCategory(petBigCategory);
        Hospital hospital = CreateEntity.createHospital();

        userRepository.save(user);
        hospitalRepository.save(hospital);
        petBigCategoryRepository.save(petBigCategory);
        petCategoryRepository.save(petCategory);


        HospitalStarRating review = CreateEntity.createReview(petCategory, user, hospital);

        HospitalStarRating savedreview = reviewRepository.save(review);
        System.out.println("savedreview = " + savedreview);
    }




}