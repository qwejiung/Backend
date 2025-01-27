package LittlePet.UMC.Badge.converter;

import LittlePet.UMC.Badge.dto.BadgeResponseDTO;
import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.UserBadge;
import LittlePet.UMC.domain.userEntity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserBadgeConverter {
    public static List<UserBadge> toUserBadgeList(List<User> userList, Badge Challenger) {
        return userList.stream()
                .map(user ->
                    UserBadge.builder()
                            .badge(Challenger)
                            .user(user)
                            .build()
                ).collect(Collectors.toList());

    }

    public static List<BadgeResponseDTO.ChallengerResultDTO> tochallengersDTO(List<UserBadge> challengerUserbadge) {
        return challengerUserbadge.stream()
                .map(challenger ->
                        BadgeResponseDTO.ChallengerResultDTO.builder()
                                .badgetype(challenger.getBadge().getName())
                                .userid(challenger.getUser().getId())
                                .createdAt(LocalDateTime.now())
                                .build()
                ).collect(Collectors.toList());
    }
}
