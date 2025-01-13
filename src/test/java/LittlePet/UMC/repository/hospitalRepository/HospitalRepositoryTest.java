package LittlePet.UMC.repository.hospitalRepository;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.repository.CreateEntity;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class HospitalRepositoryTest {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Test
    @DisplayName("Hospital Entity Create Test")
    public void HospitalEntityCreateTest() {
        Hospital hospital = CreateEntity.createHospital();

        Hospital savedHospital = hospitalRepository.save(hospital);
        System.out.println("savedHospital = " + savedHospital);

    }
}