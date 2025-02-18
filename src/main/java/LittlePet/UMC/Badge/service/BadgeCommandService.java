package LittlePet.UMC.Badge.service;

import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;
import LittlePet.UMC.domain.userEntity.User;

import java.util.List;

public interface BadgeCommandService {
    Boolean checkBadges(Long userId, String badgeType);

    UserBadge assignBadge(Long userId, String badgeType);

    List<Badge> getBadgesByUserId(Long userId);

    List<UserBadge> assignChallenger();

    UserBadge deleteUserBadge(Long userId, String badgeType);

    String getBadgeProgress(Long userId, String badgeType);

    List<String> getMissingBadges(Long userId);
}
