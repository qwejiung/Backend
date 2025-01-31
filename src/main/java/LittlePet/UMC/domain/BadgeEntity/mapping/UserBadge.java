package LittlePet.UMC.domain.BadgeEntity.mapping;

import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class UserBadge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id",nullable = false)
    private Badge badge;
    // 빌더에서 User와 UserBadge 간의 관계를 자동 설정

    @Builder
    public UserBadge(User user, Badge badge) {
        this.user = user;
        this.badge = badge;
        user.getUserBadgeList().add(this); // 관계 설정
    }
}
