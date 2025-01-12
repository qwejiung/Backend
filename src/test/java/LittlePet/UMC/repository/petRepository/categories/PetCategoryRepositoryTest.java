package LittlePet.UMC.repository.petRepository.categories;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.repository.CreateEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class PetCategoryRepositoryTest {

    @Autowired
    private PetCategoryRepository petCategoryRepository;
    @Autowired
    private PetBigCategoryRepository petBigCategoryRepository;

    PetBigCategory petBigCategory = PetBigCategory.builder()
            .categoryName("설치류")
            .build();



    @Test
    @DisplayName("PetCategory Entity Create Test")
    public void PetCategoryCreateTest() {
        //PetBigCategory와 PetCategory 엔티티 생성
        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
        PetCategory petcategory = CreateEntity.createPetCategory(petBigCategory);

        PetBigCategory savedbig = petBigCategoryRepository.save(petBigCategory);

        //만든 Petcategory DB에 저장
        PetCategory newpet_petcategory= petCategoryRepository.save(petcategory);
        System.out.println(newpet_petcategory);
    }

}

