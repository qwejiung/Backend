package LittlePet.UMC.SmallPet.dto;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

public class PetBigCategoryResponseDto {
//
//    @Builder
//    public static class WriteDto {
//        private Long id;
//        private String categoryName;
//    }

    @Builder
    public static class GetDto{
        private Long id;
        private String categoryName;
        private List<PetCategoryResponseDto.SmallInfoDto> petCategoryList = new ArrayList<>();
    }

}

//PetBigCategory는 그냥 entity 전부 다 반환하면 되는 거여서
//DTO가 굳이 필요할까/..?