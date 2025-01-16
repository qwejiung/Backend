package LittlePet.UMC.repository;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.domain.enums.RoleStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

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
                .phone("010-1234-5678")
                .gender(Gender.MALE)
                .email("john.doe@example.com")
                .socialId("john_doe_123")
                .socialProvider(SocialProviderEnum.KAKAO)
                .role(RoleStatus.USER)
                .introduction("Hello! I'm John.")
                .profilePhoto("profile.jpg")
                .build();

        User newUser = userRepository.save(user);
        System.out.println(newUser);
    }
}