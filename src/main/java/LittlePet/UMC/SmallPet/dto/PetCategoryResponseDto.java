package LittlePet.UMC.SmallPet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class PetCategoryResponseDto {
    @Builder
    public static class SmallInfoDto {
        private Long id;
        private String species;
        private String imageUrl;
    }
}
