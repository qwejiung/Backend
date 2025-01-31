package LittlePet.UMC.Badge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class BadgeResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class badgeResultDTO {
        Long userId;
        Long badgeId;
        String name;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getBadgeResultDTO {
        List<String> badgeTypeList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengerResultDTO {
        Long userid;
        String badgetype;
        LocalDateTime createdAt;

    }
}

