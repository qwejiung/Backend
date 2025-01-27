package LittlePet.UMC.repository.badgeRepository;

import LittlePet.UMC.Badge.repository.badgeRepository.BadgeRepository;
import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.BadgeEntity.Badge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class BadgeRepositoryTest {
    @Autowired
    private BadgeRepository badgeRepository;

    @Test
    @DisplayName("Badge Entity Create Test")
    public void createBadgeTest() {
        Badge badge = Badge.builder()
                .name("강건희")
                .build();

        Badge savedBadge = badgeRepository.save(badge);
        System.out.println("savedBadge = " + savedBadge);

    }


}