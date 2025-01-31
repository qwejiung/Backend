package LittlePet.UMC.SmallPet.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PetBigCategoryResponseDto {

    @Getter
    @Builder
    public static class PetBigCategoryGetDto {
        private Long id;
        private String categoryName;
        private List<PetCategoryResponseDto.PetCategoryDTO> petCategoryList = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}

//PetBigCategory는 그냥 entity 전부 다 반환하면 되는 거여서
//DTO가 굳이 필요할까/..?