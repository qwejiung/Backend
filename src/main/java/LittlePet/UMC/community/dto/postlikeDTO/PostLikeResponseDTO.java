package LittlePet.UMC.community.dto.postlikeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostLikeResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class postlikeResultDTO {
        Long userId;
        Long postId;
        LocalDateTime createdAt;
    }
}
