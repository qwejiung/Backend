package LittlePet.UMC.Badge.converter;

import LittlePet.UMC.Badge.dto.BadgeResponseDTO;
import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BadgeConverter {

    public static BadgeResponseDTO.badgeResultDTO toBadgeResponseDTO(UserBadge userBadge) {
        return BadgeResponseDTO.badgeResultDTO.builder()
                .userId(userBadge.getUser().getId())
                .badgeId(userBadge.getBadge().getId())
                .name(userBadge.getBadge().getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static BadgeResponseDTO.getBadgeResultDTO toGetBadgeResuletDTO(List<Badge> badges) {
        // badges가 null인 경우 null 반환
        if (badges == null) {
            return null;
        }

        List<String> list =badges.stream()
                .map(Badge::getName)
                .collect(Collectors.toList());

        return BadgeResponseDTO.getBadgeResultDTO.builder()
                .badgeTypeList(list).build();

    }
}
//List<BadgeType>
