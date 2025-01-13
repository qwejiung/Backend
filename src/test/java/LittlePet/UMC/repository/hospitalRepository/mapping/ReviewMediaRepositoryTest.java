package LittlePet.UMC.repository.hospitalRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.enums.MediaTypeEnum;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.Review;
import LittlePet.UMC.domain.hospitalEntity.mapping.ReviewMedia;
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

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class ReviewMediaRepositoryTest {

    @Autowired private ReviewMediaRepository reviewMediaRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private HospitalRepository hospitalRepository;
    @Autowired private PetBigCategoryRepository petBigCategoryRepository;
    @Autowired private PetCategoryRepository petCategoryRepository;

    @Test
    @DisplayName("ReviewMedia Entity Create Test")
    public void ReviewMediaEntityCreateTest() {
        //외래키(리뷰) 엔티티 생성 및 저장
        User user = CreateEntity.createUser();
        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
        PetCategory petCategory = CreateEntity.createPetCategory(petBigCategory);
        Hospital hospital = CreateEntity.createHospital();
        Review review = CreateEntity.createReview(petCategory,user,hospital);

        userRepository.save(user);
        hospitalRepository.save(hospital);
        petBigCategoryRepository.save(petBigCategory);
        petCategoryRepository.save(petCategory);
        reviewRepository.save(review);


        ReviewMedia reviewMedia = ReviewMedia.builder()
                .mediaUrl("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQVIAYlxRUr4lReRcnB1cRiKNV82RH8fhWRZMCBHb00VMABiPZUtZ-NagdBlnqBOXhmWRSBFniRuZxIDqS6fhKisJt-1g-hJyJlC2qps4Lg")
                .mediaType(MediaTypeEnum.Picture)
                .review(review)
                .build();

        ReviewMedia createdReviewMedia = reviewMediaRepository.save(reviewMedia);
        System.out.println("createdReviewMedia = " + createdReviewMedia);


    }

}