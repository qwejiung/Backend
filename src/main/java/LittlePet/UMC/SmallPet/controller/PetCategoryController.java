package LittlePet.UMC.SmallPet.controller;

import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
import LittlePet.UMC.SmallPet.service.PetBigCategoryService;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal-categories")
@RequiredArgsConstructor
public class PetCategoryController {
    private final PetBigCategoryService petBigCategoryService;

    //Category Create
    @GetMapping("")
    public ApiResponse<List<PetBigCategoryResponseDto.GetDto>> getPetBigCategory() {
        List<PetBigCategoryResponseDto.GetDto> res = petBigCategoryService.getPetBigCategories();
        return ApiResponse.onSuccess(res);
    }

    @PostMapping("")
    public ApiResponse<PetBigCategory> createPetBigCategory( PetBigCategoryRequestDto.WriteDTO request) {
        PetBigCategory res = petBigCategoryService.createPetBigCategory(request);
        return ApiResponse.onSuccess(res);
    }

    @PutMapping("")
    public  ApiResponse<PetBigCategory> updatePetBigCategory(@PathVariable("petBigCategoryId") Long petBigCategoryId, PetBigCategoryRequestDto.WriteDTO request ) {
        PetBigCategory res = petBigCategoryService.updatePetBigCategory(petBigCategoryId,request );
        return ApiResponse.onSuccess(res);
    }

    @DeleteMapping("")
    public ApiResponse<String> deletePetBigCategory(@PathVariable("petBigCategoryId") Long petBigCategoryId) {
        petBigCategoryService.deletePetBigCategory(petBigCategoryId);
        return ApiResponse.onSuccess("delete pet big category successfully");
    }

}
