package LittlePet.UMC.Badge.repository.badgeRepository;

import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;
import LittlePet.UMC.domain.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {

    Collection<UserBadge> findByUserId(Long userId);


    @Modifying
    @Query("DELETE FROM UserBadge ub WHERE ub.user.id = :userId AND ub.badge.id = :badgeId")
    void deleteByUserAndBadge(Long userId, Long badgeId);

    @Query("SELECT ub FROM UserBadge ub WHERE ub.user = :user AND ub.badge = :badge")
    UserBadge findByUserIdandBadgeType(@Param("user") User user, @Param("badge") Badge badge);

    boolean existsByUserAndBadge(User user, Badge badge);


}
