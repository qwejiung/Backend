package LittlePet.UMC.SmallPet.controller;

import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryReqeustDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryResponseDto;
import LittlePet.UMC.SmallPet.service.PetCategoryService;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal-categories")
@RequiredArgsConstructor
public class PetCategoryController {
    private final PetCategoryService petCategoryService;

    //Category Create
    @Operation(summary = "소동물 대분류 카테고리 조회", description = "소동물 대분류 카테고리 조회할 수 있는 카테고리입니다")
    @GetMapping("")
    public ApiResponse<List<PetBigCategoryResponseDto.PetBigCategoryGetDto>> getPetBigCategory() {
        List<PetBigCategoryResponseDto.PetBigCategoryGetDto> res = petCategoryService.getPetBigCategories();
        return ApiResponse.onSuccess(res);
    }
    
    @Operation(summary = "소동물 대분류 카테고리 추가", description = "소동물 대분류 카테고리를 추가할 수 있는 카테고리입니다. admin만 가능합니다.")
    @PostMapping("")
    public ApiResponse<PetBigCategory> createPetBigCategory( @RequestBody @Valid PetBigCategoryRequestDto.PetBigCategoryWriteDTO request) {
        PetBigCategory res = petCategoryService.createPetBigCategory(request);
        return ApiResponse.onSuccess(res);
    }

    @Operation(summary = "소동물 대분류 카테고리 추가", description = "소동물 대분류 카테고리를 수정할 수 있는 카테고리입니다. admin만 가능합니다.")
    @PutMapping("/{category-id}")
    public  ApiResponse<PetBigCategory> updatePetBigCategory(@PathVariable("category-id") Long petBigCategoryId, @RequestBody @Valid PetBigCategoryRequestDto.PetBigCategoryWriteDTO request ) {
        PetBigCategory res = petCategoryService.updatePetBigCategory(petBigCategoryId,request );
        return ApiResponse.onSuccess(res);
    }
    
    @Operation(summary = "소동물 대분류 카테고리 추가", description = "소동물 대분류 카테고리를 삭제할 수 있는 카테고리입니다. admin만 가능합니다.")
    @DeleteMapping("/{category-id}")
    public ApiResponse<String> deletePetBigCategory(@PathVariable("category-id") Long petBigCategoryId) {
        petCategoryService.deletePetBigCategory(petBigCategoryId);
        return ApiResponse.onSuccess("delete pet big category successfully");
    }

    @Operation(summary = "특정 소동물 카테고리의 세부 정보 조회 ", description = "특정 소동물 카테고리의 세부 정보를 조회할 수 있습니다.")
    @GetMapping("/species/{species-id}")
    public ApiResponse<PetCategoryResponseDto.PetCategoryDetailDTO> getPetCategory(@PathVariable("species-id") Long speciesId) {
        PetCategoryResponseDto.PetCategoryDetailDTO res = petCategoryService.getPetCategoryById(speciesId);
        return ApiResponse.onSuccess(res);
    }

    @Operation(summary = "소동물 카테고리 추가 ", description = "특정 소동물 카테고리를 추가할 수 있습니다. admin만 가능합니다. (s3 구축 후 api 보완 예정. 우선 image 관련 처리 X)")
    @PostMapping("/species")
    public ApiResponse<PetCategoryResponseDto.PetCategoryDetailDTO> createPetCategory(@RequestBody @Valid PetCategoryReqeustDto.PetCategoryWriteDTO request){
        PetCategoryResponseDto.PetCategoryDetailDTO res = petCategoryService.createPetCategory(request);
        return ApiResponse.onSuccess(res);
    }

    @Operation(summary = "소동물 카테고리 수정 ", description = "특정 소동물 카테고리를 수정할 수 있습니다. admin만 가능합니다. (s3 구축 후 api 보완 예정. 임시 image url 반환.)")
    @PutMapping("/species/{species-id}")
    public ApiResponse<PetCategoryResponseDto.PetCategoryDetailDTO> updatePetCategory(@PathVariable("species-id") Long speciesId , @RequestBody @Valid PetCategoryReqeustDto.PetCategoryWriteDTO request){
        PetCategoryResponseDto.PetCategoryDetailDTO res = petCategoryService.updatePetCategory(speciesId,request);
        return ApiResponse.onSuccess(res);
    }

    @Operation(summary = "소동물 카테고리 삭제 ", description = "특정 소동물 카테고리를 삭제할 수 있습니다. admin만 가능합니다. (s3 구축 후 api 보완 예정. 임시 image url 반환.)")
    @DeleteMapping("/species/{species-id}")
    public ApiResponse<String> deletePetCategory(@PathVariable("species-id") Long speciesId) {
        petCategoryService.deletePetCategory(speciesId);
        return ApiResponse.onSuccess("delete pet category successfully");
    }

}
