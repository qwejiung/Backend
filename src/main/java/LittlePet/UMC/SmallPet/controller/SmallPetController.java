package LittlePet.UMC.SmallPet.controller;

import LittlePet.UMC.SmallPet.dto.PetBigCategoryRequestDto;
import LittlePet.UMC.SmallPet.dto.PetBigCategoryResponseDto;
import LittlePet.UMC.SmallPet.service.PetBigCategoryService;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/animal-categories")
@RequiredArgsConstructor
public class SmallPetController {
    private final PetBigCategoryService petBigCategoryService;

    //Category Create
    @GetMapping("")
    public ApiResponse<List<PetBigCategoryResponseDto.GetDto>> getPetBigCategory() {
        List<PetBigCategoryResponseDto.GetDto> res = petBigCategoryService.getPetBigCategories();
        return ApiResponse.onSuccess(res);
    }

    @PostMapping("")
    public ApiResponse<PetBigCategory> createPetBigCategory(@RequestBody PetBigCategoryRequestDto petBigCategoryRequestDto) {

    }

}
