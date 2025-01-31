package LittlePet.UMC.SmallPet.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PetCategoryResponseDto {

    @Getter
    @Builder
    public static class PetCategoryDTO {
        private Long id;
        private String species;
        private String imageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Builder
    public static class PetCategoryDetailDTO {
        private Long id;
        private String species;

        @Nullable
        private String features;
        @Nullable
        private String featuresHeadLine;

        //먹이정보
        @Nullable
        private String foodInfo;
        @Nullable
        private String foodInfoHeadLine;

        //환경
        @Nullable
        private String environment;
        @Nullable
        private String environmentHeadLine;

        //놀이방법
        @Nullable
        private String playMethods;
        @Nullable
        private String playMethodsHeadLine;

        @Nullable
        private String featureImagePath;

        private Long petBigCategoryId;

        private String petBigCategoryName;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;


    }
}
