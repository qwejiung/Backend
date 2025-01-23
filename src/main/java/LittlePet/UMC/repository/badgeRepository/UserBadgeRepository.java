package LittlePet.UMC.repository.badgeRepository;

import LittlePet.UMC.domain.BadgeEntity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {
}
