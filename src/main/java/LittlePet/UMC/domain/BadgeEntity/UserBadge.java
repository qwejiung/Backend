package LittlePet.UMC.domain.BadgeEntity;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
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
}
