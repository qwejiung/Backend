package LittlePet.UMC.SmallPet.service;

import LittlePet.UMC.SmallPet.converter.PetBigCategoryConverter;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
import LittlePet.UMC.SmallPet.repository.PetBigCategoryRepository;
import LittlePet.UMC.SmallPet.repository.PetCategoryRepository;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.apiPayload.exception.handler.PetBigCategoryHandler;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetBigCategoryService {
    private final PetBigCategoryRepository petBigCategoryRepository;
    private final PetCategoryRepository petCategoryRepository;

    @Transactional
    public PetBigCategory validId(Long id){
        return petBigCategoryRepository.findById(id)
                .orElseThrow(() -> new PetBigCategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
    }

    //모든 petbigcategory 가져오기.
    @Transactional(readOnly = true)
    public List<PetBigCategoryResponseDto.GetDto> getPetBigCategories() {
        return petBigCategoryRepository.findAll().stream()
                .map(PetBigCategoryConverter::toGetDto)
                .collect(Collectors.toList());
    }

    @Transactional
    //pet BigCategory 추가에 따른 service 로직 처리
    public PetBigCategory createPetBigCategory(PetBigCategoryRequestDto.WriteDTO request){
        PetBigCategory newEntity = PetBigCategoryConverter.toEntity(request);
        petBigCategoryRepository.save(newEntity);

        return newEntity;
    }
//
    //PetBig Category 수정에 따른 service 로직 추가
    //validation을 따로 둘지 아니면 그냥 service에서 처리할지 고민.
    //일단 validation하는 게 두 곳에 존재하는게 보기 싫
    @Transactional
    public PetBigCategory updatePetBigCategory(Long id,PetBigCategoryRequestDto.WriteDTO request){
        PetBigCategory petBigCategory = this.validId(id);

        petBigCategory.setCategoryName(request.getCategoryName());
        return petBigCategoryRepository.save(petBigCategory);

    }

    @Transactional
    public void deletePetBigCategory(Long id,PetBigCategoryRequestDto.WriteDTO request){
        PetBigCategory petBigCategory = this.validId(id);
        petBigCategoryRepository.delete(petBigCategory);
    }


}
