package LittlePet.UMC.repository;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.User;
import LittlePet.UMC.domain.enums.RoleStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("User Entity Create Test")
    public void createUserTest() {
        User user = User.builder()
                .name("John Doe")
                .phone("1234567890")
                .email("johndoe@example.com")
                .role(RoleStatus.USER)
                .build();
        User newUser = userRepository.save(user);
        System.out.println(newUser);
    }
}