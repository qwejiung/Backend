package LittlePet.UMC.SmallPet.converter;

import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryResponseDto;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetBigCategoryConverter {

    public PetBigCategory toEntity(PetBigCategoryRequestDto dto) {
        return PetBigCategory.builder()
                .categoryName(dto.getCategoryName())
                .build();
    }

    public PetBigCategoryResponseDto.CreateAndUpdateDto toCreateAndUpdateDto(PetBigCategory entity) {
        return PetBigCategoryResponseDto.CreateAndUpdateDto.builder()
                .id(entity.getId())
                .categoryName(entity.getCategoryName())
                .build();
    }

    public PetBigCategoryResponseDto.GetDto toGetDto(PetBigCategory entity) {
        List<PetCategoryResponseDto.SmallInfoDto> smallInfoDtoList =
                entity.getPetCategoryList().stream()
                        .map(petCategory -> PetCategoryResponseDto.SmallInfoDto.builder()
                                .id(petCategory.getId())
                                .species(petCategory.getSpecies())
                                .imageUrl(petCategory.getFeatureImagePath())
                                .build()
                        )
                        .toList();

        return PetBigCategoryResponseDto.GetDto.builder()
                .id(entity.getId())
                .categoryName(entity.getCategoryName())
                .petCategoryList(smallInfoDtoList)
                .build();
    }


}
