package LittlePet.UMC.Badge.repository.badgeRepository;

import LittlePet.UMC.domain.BadgeEntity.Badge;

public interface BadgeRepositoryCustom {
//    Optional<UserBadge> findByUserIdAndBadgeId(Long userId, Long badgeId);
//    List<UserBadge> findByUserId(Long userId);

    Badge findByBadgeType(String badgeType);


}
