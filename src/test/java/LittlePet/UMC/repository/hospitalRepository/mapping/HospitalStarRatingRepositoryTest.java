package LittlePet.UMC.repository.hospitalRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalStarRating;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.CreateEntity;
import LittlePet.UMC.repository.UserRepository;
import LittlePet.UMC.repository.hospitalRepository.HospitalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class HospitalStarRatingRepositoryTest {
    //리포지토리들
    @Autowired
    private HospitalPrefRepository hospitalPrefRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HospitalStarRatingRepository hospitalStarRatingRepository;

    @Test
    @DisplayName("HospitalStarRating Entity Create Test")
    public void HospitalStarRatingEntityCreateTest() {
        //외래키 entity 생성 및 DB 저장
        User user = CreateEntity.createUser();
        Hospital hospital = CreateEntity.createHospital();
        userRepository.save(user);
        hospitalRepository.save(hospital);

        //HospitalStarRating entity 생성
        HospitalStarRating hospitalStarRating = HospitalStarRating.builder()
                .rating(5)
                .user(user)
                .hospital(hospital)
                .build();

        HospitalStarRating savedrating= hospitalStarRatingRepository.save(hospitalStarRating);
        System.out.println("HospitalStarRating Entity Create Test"+savedrating);


    }

}