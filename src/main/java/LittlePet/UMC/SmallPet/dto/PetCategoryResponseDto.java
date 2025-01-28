package LittlePet.UMC.SmallPet.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

public class PetCategoryResponseDto {
    @Getter
    @Builder
    public static class PetCategoryDTO {
        private Long id;
        private String species;
        private String imageUrl;
    }

    @Getter
    @Builder
    public static class PetCategoryDetailDTO {
        private Long id;
        private String species;

        @Nullable
        private String features;

        @Nullable
        private String foodInfo;

        @Nullable
        private String environment;

        @Nullable
        private String playMethods;

        @Nullable
        private String featureImagePath;

        private Long petBigCategoryId;

        private String petBigCategoryName;
    }
}
