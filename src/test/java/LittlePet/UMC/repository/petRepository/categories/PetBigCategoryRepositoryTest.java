package LittlePet.UMC.repository.petRepository.categories;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.enums.PostType;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.postEntity.PostCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class PetBigCategoryRepositoryTest {

    @Autowired
    private PetBigCategoryRepository petBigCategoryRepository;

    @Test
    @DisplayName("PetBigCategory Entity Create Test")
    public void PetBigCategoryCreateTest() {

        PetBigCategory petbigcategory = PetBigCategory.builder()
                .categoryName("설치류")
                .build();
        PetBigCategory newpet_bigcategory= petBigCategoryRepository.save(petbigcategory);
        System.out.println(newpet_bigcategory);
    }

}