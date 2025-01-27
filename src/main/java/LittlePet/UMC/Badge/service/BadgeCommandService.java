package LittlePet.UMC.Badge.service;

import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.UserBadge;
import LittlePet.UMC.domain.userEntity.User;

import java.util.List;

public interface BadgeCommandService {
    UserBadge checkBadges(Long userId, String badgeType);

    UserBadge assignBadge(User user, String badgeType, boolean criteriaMet);

    List<Badge> getBadgesByUserId(Long userId);

    List<UserBadge> assignChallenger();

    UserBadge deleteUserBadge(Long userId, String badgeType);

}
