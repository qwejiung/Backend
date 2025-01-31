package LittlePet.UMC.SmallPet.controller;

import LittlePet.UMC.S3Service;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/animal-categories")
@RequiredArgsConstructor
public class PetCategoryController {
    private final PetCategoryService petCategoryService;
    private final S3Service s3Service;
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

    @Operation(summary = "소동물 대분류 카테고리 수정", description = "소동물 대분류 카테고리를 수정할 수 있는 카테고리입니다. admin만 가능합니다.")
    @PutMapping("/{category-id}")
    public  ApiResponse<PetBigCategory> updatePetBigCategory(@PathVariable("category-id") Long petBigCategoryId, @RequestBody @Valid PetBigCategoryRequestDto.PetBigCategoryWriteDTO request ) {
        PetBigCategory res = petCategoryService.updatePetBigCategory(petBigCategoryId,request );
        return ApiResponse.onSuccess(res);
    }
    
    @Operation(summary = "소동물 대분류 카테고리 삭제", description = "소동물 대분류 카테고리를 삭제할 수 있는 카테고리입니다. admin만 가능합니다.")
    @DeleteMapping("/{category-id}")
    public ApiResponse<String> deletePetBigCategory(@PathVariable("category-id") Long petBigCategoryId) {
        petCategoryService.deletePetBigCategory(petBigCategoryId);
        return ApiResponse.onSuccess("delete pet big category successfully");
    }

    @Operation(summary = "소동물 카테고리의 세부 정보 조회 ", description = "소동물 카테고리의 세부 정보를 조회할 수 있습니다. 쿼리를 통해 특정 소동물 카테고리를 조회할 수 있습니다.")
    @GetMapping("/species")
    public ApiResponse<Object> getPetCategory(@RequestParam(value = "species-id", required = false) Long speciesId) {
        Object res;

        if(speciesId == null){
            res = petCategoryService.getPetCategoryAll();
        }
        else{
            res = petCategoryService.getPetCategoryById(speciesId);
        }
        return ApiResponse.onSuccess(res);
    }

    @Operation(summary = "소동물 카테고리 추가", description = "특정 소동물 카테고리를 추가할 수 있습니다. admin만 가능합니다. 스웨거로 테스트시 오류 -> 보완예정. (type 인식 문제.)")
    @PostMapping(value = "/species", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<PetCategoryResponseDto.PetCategoryDetailDTO> createPetCategory(
            @RequestPart("category-form") PetCategoryReqeustDto.PetCategoryWriteDTO request,
            @RequestPart("image") MultipartFile image) throws IOException {
        {
            String imageUrl = s3Service.upload(image);

            PetCategoryResponseDto.PetCategoryDetailDTO res = petCategoryService.createPetCategory(request,imageUrl);
            return ApiResponse.onSuccess(res);
        }
    }
    @Operation(summary = "소동물 카테고리 수정 ", description = "특정 소동물 카테고리를 수정할 수 있습니다. admin만 가능합니다.")
    @PutMapping("/species/{species-id}")
    public ApiResponse<PetCategoryResponseDto.PetCategoryDetailDTO> updatePetCategory(@PathVariable("species-id") Long speciesId , @RequestBody @Valid PetCategoryReqeustDto.PetCategoryWriteDTO request){
        PetCategoryResponseDto.PetCategoryDetailDTO res = petCategoryService.updatePetCategory(speciesId,request);
        return ApiResponse.onSuccess(res);
    }

    @Operation(summary = "소동물 카테고리 삭제 ", description = "특정 소동물 카테고리를 삭제할 수 있습니다. admin만 가능합니다")
    @DeleteMapping("/species/{species-id}")
    public ApiResponse<String> deletePetCategory(@PathVariable("species-id") Long speciesId) {
        petCategoryService.deletePetCategory(speciesId);
        return ApiResponse.onSuccess("delete pet category successfully");
    }

}
