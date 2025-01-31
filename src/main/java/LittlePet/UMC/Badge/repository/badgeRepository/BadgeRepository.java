package LittlePet.UMC.Badge.repository.badgeRepository;

import LittlePet.UMC.domain.BadgeEntity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BadgeRepository extends JpaRepository<Badge, Long> ,BadgeRepositoryCustom {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Badge b WHERE b.name = :badgeType")
    boolean existByBadgeType(@Param("badgeType") String badgeType);



}
