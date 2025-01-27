package LittlePet.UMC.User.controller;

import LittlePet.UMC.User.dto.PetProfileRequest.PetProfileRequestDTO;
import LittlePet.UMC.User.dto.PetProfileResponse.PetProfileResponseDTO;
import LittlePet.UMC.User.service.PetProfileService;
import LittlePet.UMC.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/pets")
@RequiredArgsConstructor
public class PetProfileController {

    private final PetProfileService petProfileService;

    /**
     * 반려동물 프로필 등록 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @param request 반려동물 프로필 요청 DTO
     * @return 추가된 반려동물 프로필 응답 DTO
     */
    @Operation(summary = "반려동물 프로필 등록", description = "사용자가 새로운 반려동물 프로필을 등록할 수 있는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필 등록 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ApiResponse<PetProfileResponseDTO> addPetProfile(
            @PathVariable Long userId,
            @RequestBody @Valid PetProfileRequestDTO request
    ) {
        PetProfileResponseDTO response = petProfileService.addPetProfile(userId, request);
        return ApiResponse.onSuccess(response);
    }

    /**
     * 반려동물 프로필 수정 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @param petId 반려동물 ID (PathVariable)
     * @param request 반려동물 프로필 요청 DTO
     * @return 수정된 반려동물 프로필 응답 DTO
     */
    @Operation(summary = "반려동물 프로필 수정", description = "사용자가 반려동물 프로필을 수정할 수 있는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필 수정 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 반려동물을 찾을 수 없음", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{petId}")
    public ApiResponse<PetProfileResponseDTO> updatePetProfile(
            @PathVariable Long userId,
            @PathVariable Long petId,
            @RequestBody @Valid PetProfileRequestDTO request
    ) {
        PetProfileResponseDTO response = petProfileService.updatePetProfile(userId, petId, request);
        return ApiResponse.onSuccess(response);
    }

    /**
     * 반려동물 프로필 삭제 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @param petId 반려동물 ID (PathVariable)
     * @return 삭제 성공 메시지
     */
    @Operation(summary = "반려동물 프로필 삭제", description = "사용자가 반려동물 프로필을 삭제할 수 있는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필 삭제 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 반려동물을 찾을 수 없음", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{petId}")
    public ApiResponse<String> deletePetProfile(
            @PathVariable Long userId,
            @PathVariable Long petId
    ) {
        petProfileService.deletePetProfile(userId, petId);
        return ApiResponse.onSuccess("반려동물 프로필이 성공적으로 삭제되었습니다.");
    }
}
