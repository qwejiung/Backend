//package LittlePet.UMC.SmallPet.service;
//
//import LittlePet.UMC.SmallPet.converter.PetBigCategoryConverter;
//import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
//import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
//import LittlePet.UMC.SmallPet.repository.PetBigCategoryRepository;
//import LittlePet.UMC.SmallPet.repository.PetCategoryRepository;
//import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class PetBigCategoryService {
//    private final PetBigCategoryRepository petBigCategoryRepository;
//    private final PetCategoryRepository petCategoryRepository;
//    private final PetBigCategoryConverter petBigCategoryConverter;
//
//    @Transactional(readOnly = true)
//    public PetBigCategoryResponseDto.GetDto getPetBigCategory(){
//        List<PetBigCategory> petBigCategoryList = petBigCategoryRepository.findAll();
//        return (PetBigCategoryResponseDto.GetDto) petBigCategoryList.stream()
//                .map(petBigCategoryConverter::toGetDto)
//                .collect(Collectors.toList());
//    }
//
//    //pet BigCategory 추가에 따른 service 로직 처리
//    @Transactional
//    public PetBigCategoryResponseDto.CreateAndUpdateDto createPetBigCategory(PetBigCategoryRequestDto dto){
//        PetBigCategory petBigCategory = petBigCategoryConverter.toEntity(dto);
//        PetBigCategory savedPetBigCategory = petBigCategoryRepository.save(petBigCategory);
//
//        return petBigCategoryConverter.toCreateAndUpdateDto(savedPetBigCategory);
//    }
//
//    //PetBig Category 수정에 따른 service 로직 추가
//    @Transactional
//    public PetBigCategoryResponseDto.CreateAndUpdateDto createPetBigCategory(PetBigCategoryRequestDto dto){
//        PetBigCategory petBigCategory = petBigCategoryConverter.toEntity(dto);
//        PetBigCategory savedPetBigCategory = petBigCategoryRepository.save(petBigCategory);
//
//        return petBigCategoryConverter.toCreateAndUpdateDto(savedPetBigCategory);
//    }
//
//
//
//}
