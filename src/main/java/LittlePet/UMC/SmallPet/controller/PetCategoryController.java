package LittlePet.UMC.SmallPet.controller;

import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryReqeustDto;
import LittlePet.UMC.SmallPet.dto.PetCategoryResponseDto;
import LittlePet.UMC.SmallPet.service.PetCategoryService;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
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
    @GetMapping("")
    public ApiResponse<List<PetBigCategoryResponseDto.GetDto>> getPetBigCategory() {
        List<PetBigCategoryResponseDto.GetDto> res = petCategoryService.getPetBigCategories();
        return ApiResponse.onSuccess(res);
    }

    @PostMapping("")
    public ApiResponse<PetBigCategory> createPetBigCategory( @RequestBody @Valid PetBigCategoryRequestDto.PetBigCategoryWriteDTO request) {
        PetBigCategory res = petCategoryService.createPetBigCategory(request);
        return ApiResponse.onSuccess(res);
    }

    @PutMapping("/{category-id}")
    public  ApiResponse<PetBigCategory> updatePetBigCategory(@PathVariable("category-id") Long petBigCategoryId, @RequestBody @Valid PetBigCategoryRequestDto.PetBigCategoryWriteDTO request ) {
        PetBigCategory res = petCategoryService.updatePetBigCategory(petBigCategoryId,request );
        return ApiResponse.onSuccess(res);
    }

    @DeleteMapping("/{category-id}")
    public ApiResponse<String> deletePetBigCategory(@PathVariable("category-id") Long petBigCategoryId) {
        petCategoryService.deletePetBigCategory(petBigCategoryId);
        return ApiResponse.onSuccess("delete pet big category successfully");
    }

    @GetMapping("/species/{species-id}")
    public ApiResponse<PetCategoryResponseDto.DTO> getPetCategory(@PathVariable("species-id") Long speciesId) {
        PetCategoryResponseDto.DTO res = petCategoryService.getPetCategoryById(speciesId);
        return ApiResponse.onSuccess(res);
    }

    @PostMapping("/species")
    public ApiResponse<PetCategoryResponseDto.DTO> createPetCategory(@RequestBody @Valid PetCategoryReqeustDto.PetCategoryWriteDTO request){
        PetCategoryResponseDto.DTO res = petCategoryService.createPetCategory(request);
        return ApiResponse.onSuccess(res);
    }

    @PutMapping("/species/{species-id}")
    public ApiResponse<PetCategoryResponseDto.DTO> updatePetCategory(@PathVariable("species-id") Long speciesId ,@RequestBody @Valid PetCategoryReqeustDto.PetCategoryWriteDTO request){
        PetCategoryResponseDto.DTO res = petCategoryService.updatePetCategory(speciesId,request);
        return ApiResponse.onSuccess(res);
    }

    @DeleteMapping("/species/{species-id}")
    public ApiResponse<String> deletePetCategory(@PathVariable("species-id") Long speciesId) {
        petCategoryService.deletePetCategory(speciesId);
        return ApiResponse.onSuccess("delete pet category successfully");
    }

}
