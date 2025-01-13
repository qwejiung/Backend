package LittlePet.UMC.repository.petRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.CreateEntity;
import LittlePet.UMC.repository.UserRepository;
import LittlePet.UMC.repository.petRepository.categories.PetBigCategoryRepository;
import LittlePet.UMC.repository.petRepository.categories.PetCategoryRepository;
import LittlePet.UMC.repository.postRepository.PostCategoryRepository;
import LittlePet.UMC.repository.postRepository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class UserPetRepositoryTest {
    // 리포지토리들

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetBigCategoryRepository petBigCategoryRepository;
    @Autowired
    private PetCategoryRepository petCategoryRepository;
    @Autowired
    private UserPetRepository userPetRepository;



    @Test
    @DisplayName("UserPet Entity Create Test")
    public void UserPetEntityCreateTest() {
        User user = CreateEntity.createUser();
        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
        PetCategory petCategory = CreateEntity.createPetCategory(petBigCategory);

        // 외래키 엔티티 저장
        userRepository.save(user);
        petBigCategoryRepository.save(petBigCategory);
        petCategoryRepository.save(petCategory);

        //UserPet 엔티티 생성 및 저장
        UserPet userpet = CreateEntity.createUserPet(petCategory,user);
        UserPet savedUserPet = userPetRepository.save(userpet);
        System.out.println("savedUserPet = " + savedUserPet);




    }

}