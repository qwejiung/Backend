package LittlePet.UMC.repository.badgeRepository;

import LittlePet.UMC.domain.BadgeEntity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {
}
