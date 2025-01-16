package LittlePet.UMC.repository.hospitalRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.CreateEntity;
import LittlePet.UMC.repository.UserRepository;
import LittlePet.UMC.repository.hospitalRepository.HospitalRepository;
import org.apache.catalina.Host;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class HospitalPrefRepositoryTest {
    //리포지토리
    @Autowired
    private HospitalPrefRepository hospitalPrefRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private UserRepository userRepository;

    //테스트
    @Test
    @DisplayName("HospitalPref Entity Create Test")
    public void HospitalPrefEntityCreateTest() {
        //외래키 엔티티 생성 및 저장
        User user = CreateEntity.createUser();
        Hospital hospital = CreateEntity.createHospital();

        userRepository.save(user);
        hospitalRepository.save(hospital);

//        HospitalPref 엔티티 생성 및 저장
        HospitalPref hospitalPref = HospitalPref.builder()
                .user(user)
                .hospital(hospital)
                .build();

        HospitalPref savedHospitalPref = hospitalPrefRepository.save(hospitalPref);
        System.out.println("HospitalPref saved: " + savedHospitalPref);
    }



}