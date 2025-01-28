package LittlePet.UMC.SmallPet.converter;

import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryResponseDto;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetBigCategoryConverter {

    public static PetBigCategory toEntity(PetBigCategoryRequestDto.PetBigCategoryWriteDTO dto) {
        return PetBigCategory.builder()
                .categoryName(dto.getCategoryName())
                .build();
    }
    //small pet info detail 제외.
    public static PetBigCategoryResponseDto.PetBigCategoryGetDto toGetDto(PetBigCategory entity) {
        List<PetCategoryResponseDto.PetCategoryDTO> petCategoryDTOList =
                entity.getPetCategoryList().stream()
                        .map(petCategory -> PetCategoryResponseDto.PetCategoryDTO.builder()
                                .id(petCategory.getId())
                                .species(petCategory.getSpecies())
                                .imageUrl(petCategory.getFeatureImagePath())
                                .build()
                        )
                        .toList();

        return PetBigCategoryResponseDto.PetBigCategoryGetDto.builder()
                .id(entity.getId())
                .categoryName(entity.getCategoryName())
                .petCategoryList(petCategoryDTOList)
                .build();
    }
}
