package LittlePet.UMC.repository.badgeRepository;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.UserBadge;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.CreateEntity;
import LittlePet.UMC.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class UserBadgeRepositoryTest {
    @Autowired
    private UserBadgeRepository userBadgeRepository;
    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Badge Entity Create Test")
    public void createBadgeTest() {
        //외래키 Entity 생성 및 DB 저장
        Badge badge = CreateEntity.createBadge();
        User user = CreateEntity.createUser();
        badgeRepository.save(badge);
        userRepository.save(user);

        //UserBadge 생성 및 DB저장
        UserBadge userBadge = UserBadge.builder()
                .user(user)
                .badge(badge)
                .build();

        UserBadge savedUserBadge =  userBadgeRepository.save(userBadge);
        System.out.println("UserBadge saved: " + savedUserBadge);
    }

}