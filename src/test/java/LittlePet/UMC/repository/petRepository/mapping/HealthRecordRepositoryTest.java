package LittlePet.UMC.repository.petRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.enums.FecesStatusEnum;
import LittlePet.UMC.domain.enums.HealthStatusEnum;
import LittlePet.UMC.domain.enums.MealAmountEnum;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.CreateEntity;
import LittlePet.UMC.repository.UserRepository;
import LittlePet.UMC.repository.petRepository.categories.PetBigCategoryRepository;
import LittlePet.UMC.repository.petRepository.categories.PetCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class HealthRecordRepositoryTest {
    // 리포지토리들

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetBigCategoryRepository petBigCategoryRepository;
    @Autowired
    private PetCategoryRepository petCategoryRepository;
    @Autowired
    private UserPetRepository userPetRepository;
    @Autowired
    private HealthRecordRepository healthRecordRepository;


    @Test
    @DisplayName("HealthRecord Entity Create Test")
    public void HealthRecordEntityCreateTest() {
        User user = CreateEntity.createUser();
        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
        PetCategory petCategory = CreateEntity.createPetCategory(petBigCategory);
        UserPet userpet = CreateEntity.createUserPet(petCategory, user);

        // 외래키 엔티티 저장
        userRepository.save(user);
        petBigCategoryRepository.save(petBigCategory);
        petCategoryRepository.save(petCategory);
        userPetRepository.save(userpet);

        HealthRecord healthRecord = HealthRecord.builder()
                .recordDate(LocalDate.of(2025, 1, 12))
                .weight(12.5)
                .mealAmount(MealAmountEnum.LIGHT)
                .fecesStatus(FecesStatusEnum.NORMAL)
                .healthStatus(HealthStatusEnum.COUGH)
                .abnormalSymptoms("이상 증세 딱히 없음")
                .userPet(userpet)
                .build();

        HealthRecord savedHealthRecord = healthRecordRepository.save(healthRecord);
        System.out.println("savedHealthRecord = " + savedHealthRecord.toString());





    }

}