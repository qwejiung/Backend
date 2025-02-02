package LittlePet.UMC.SmallPet.service;

import LittlePet.UMC.SmallPet.converter.PetBigCategoryConverter;
import LittlePet.UMC.SmallPet.converter.PetCategoryConverter;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryReqeustDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryResponseDto;
import LittlePet.UMC.SmallPet.repository.PetBigCategoryRepository;
import LittlePet.UMC.SmallPet.repository.PetCategoryRepository;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.apiPayload.exception.handler.PetBigCategoryHandler;
import LittlePet.UMC.apiPayload.exception.handler.PetCategoryHandler;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetCategoryService {
    private final PetBigCategoryRepository petBigCategoryRepository;
    private final PetCategoryRepository petCategoryRepository;

    public PetBigCategory isValidPetBigCategoryId(Long id){
        return petBigCategoryRepository.findById(id)
                .orElseThrow(() -> new PetBigCategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
    }

    //모든 petbigcategory 가져오기.
    @Transactional(readOnly = true)
    public List<PetBigCategoryResponseDto.PetBigCategoryGetDto> getPetBigCategories() {
        return petBigCategoryRepository.findAll().stream()
                .map(PetBigCategoryConverter::toGetDto)
                .collect(Collectors.toList());
    }

    @Transactional
    //pet BigCategory 추가에 따른 service 로직 처리
    public PetBigCategory createPetBigCategory(PetBigCategoryRequestDto.PetBigCategoryWriteDTO request){
        PetBigCategory newEntity = PetBigCategoryConverter.toEntity(request);
        petBigCategoryRepository.save(newEntity);

        return newEntity;
    }
//
    //PetBig Category 수정에 따른 service 로직 추가
    //validation을 따로 둘지 아니면 그냥 service에서 처리할지 고민.
    //일단 validation하는 게 두 곳에 존재하는게 보기 싫
    @Transactional
    public PetBigCategory updatePetBigCategory(Long id, PetBigCategoryRequestDto.PetBigCategoryWriteDTO request){
        PetBigCategory petBigCategory = this.isValidPetBigCategoryId(id);
        petBigCategory.setCategoryName(request.getCategoryName());
        return petBigCategory;
    }

    public void deletePetBigCategory(Long id){
        PetBigCategory petBigCategory = this.isValidPetBigCategoryId(id);
        petBigCategoryRepository.delete(petBigCategory);
    }


    //소동물 정보 관련 service

    //모든 category만 띱 특정 id 세부정보만 띱해오는 거
//    @Transactional(readOnly = true)
//    public List<PetCategoryResponseDto.GetDTO> getPetCategories() {
//
//    }

    public PetCategory isValidPetCategoryId(Long id){
        return petCategoryRepository.findById(id)
                .orElseThrow(()-> new PetCategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public PetCategoryResponseDto.PetCategoryDetailDTO getPetCategoryById(Long id){
        PetCategory petCategory = this.isValidPetCategoryId(id);

        return PetCategoryConverter.toResponseDTO(petCategory);
    }

    @Transactional(readOnly = true)
    public List<PetCategoryResponseDto.PetCategoryDTO> getPetCategoryAll(){
        List<PetCategory> petCategoryList = this.petCategoryRepository.findAll();

        return petCategoryList.stream()
                .map(PetCategoryConverter::toShortResponseDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public PetCategoryResponseDto.PetCategoryDetailDTO createPetCategory(PetCategoryReqeustDto.PetCategoryWriteDTO request, String url){
        PetBigCategory bigCategory = this.isValidPetBigCategoryId(request.getPetBigCategoryId());

        PetCategory newEntity = PetCategoryConverter.toEntity(request,bigCategory,url);
        petCategoryRepository.save(newEntity);

        return PetCategoryConverter.toResponseDTO(newEntity);
    }

    @Transactional
    //유효성 검증을 service에서만 처리할 수 있도록 하였습니다.
    public PetCategoryResponseDto.PetCategoryDetailDTO updatePetCategory(Long id, PetCategoryReqeustDto.PetCategoryWriteDTO request){
        PetCategory petCategory = this.isValidPetCategoryId(id);

        if(request.getFeatures() != null){
            petCategory.setFeatures(request.getFeatures());
        }
        if(request.getEnvironment() != null){
            petCategory.setEnvironment(request.getEnvironment());
        }
        if(request.getPlayMethods() != null){
            petCategory.setPlayMethods(request.getPlayMethods());
        }
        if(request.getSpecies() != null){
            petCategory.setSpecies(request.getSpecies());
        }
        if(request.getPetBigCategoryId() != null){
            PetBigCategory petBigCategory = this.isValidPetBigCategoryId(request.getPetBigCategoryId());
            petCategory.setPetBigCategory(petBigCategory);
        }
        if(request.getFoodInfo() != null){
            petCategory.setFoodInfo(request.getFoodInfo());
        }

        return PetCategoryConverter.toResponseDTO(petCategory);
    }

    public void deletePetCategory(Long id){
        PetCategory petCategory = this.isValidPetCategoryId(id);
        petCategoryRepository.delete(petCategory);
    }
}
