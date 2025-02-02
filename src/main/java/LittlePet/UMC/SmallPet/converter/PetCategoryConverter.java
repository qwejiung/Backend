package LittlePet.UMC.SmallPet.converter;

import LittlePet.UMC.SmallPet.dto.PetCategoryReqeustDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryResponseDto;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import org.springframework.stereotype.Component;

@Component
public class PetCategoryConverter {
    public static PetCategoryResponseDto.PetCategoryDetailDTO toResponseDTO(PetCategory petCategory) {
        return PetCategoryResponseDto.PetCategoryDetailDTO.builder()
                .id(petCategory.getId())
                .species(petCategory.getSpecies())
                .petBigCategoryId(petCategory.getPetBigCategory().getId())
                .petBigCategoryName(petCategory.getPetBigCategory().getCategoryName())
                .environment(petCategory.getEnvironment())
                .featureImagePath(petCategory.getFeatureImagePath())
                .features(petCategory.getFeatures())
                .title(petCategory.getTitle())
                .playMethods(petCategory.getPlayMethods())
                .foodInfo(petCategory.getFoodInfo())
                .createdAt(petCategory.getCreatedAt())
                .updatedAt(petCategory.getUpdatedAt())
                .build();
    }
    public static PetCategoryResponseDto.PetCategoryDTO toShortResponseDTO(PetCategory petCategory) {
        return PetCategoryResponseDto.PetCategoryDTO.builder()
                .id(petCategory.getId())
                .species(petCategory.getSpecies())
                .imageUrl(petCategory.getFeatureImagePath())
                .createdAt(petCategory.getCreatedAt())
                .updatedAt(petCategory.getUpdatedAt())
                .build();
    }

    public static PetCategory toEntity(PetCategoryReqeustDto.PetCategoryWriteDTO dto, PetBigCategory petBigCategory, String imagePathUrl ) {
        return PetCategory.builder()
                .features(dto.getFeatures())
                .environment(dto.getEnvironment())
                .foodInfo(dto.getFoodInfo())
                .species(dto.getSpecies())
                .petBigCategory(petBigCategory)
                .featureImagePath(imagePathUrl)
                .playMethods(dto.getPlayMethods())
                .build();
    }
}
