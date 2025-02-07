package LittlePet.UMC.User.controller;

import LittlePet.UMC.S3Service;
import LittlePet.UMC.User.dto.PetProfileRequest.PetProfileRequestDTO;
import LittlePet.UMC.User.dto.PetProfileResponse.PetProfileAllResponseDTO;
import LittlePet.UMC.User.dto.PetProfileResponse.PetProfileResponseDTO;
import LittlePet.UMC.User.service.PetProfileService;
import LittlePet.UMC.apiPayload.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PetProfileController {

    private final PetProfileService petProfileService;
    private final S3Service s3Service;  // S3Service 주입


    /**
     * 반려동물 프로필 등록 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @return 추가된 반려동물 프로필 응답 DTO
     */
    @Operation(summary = "반려동물 프로필 등록", description = "사용자가 새로운 반려동물 프로필을 등록할 수 있는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필 등록 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(mediaType = "application/json"))
    })
    @PostMapping(value = "/api/users/{userId}/pets",consumes = {"multipart/form-data"})
    public ApiResponse<PetProfileResponseDTO> addPetProfile(
            @PathVariable Long userId,
            @RequestPart(value = "request",required = true) String requestJson,
            @RequestPart(value = "file", required = false) MultipartFile file // 이미지 (선택)
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PetProfileRequestDTO request;
        try {
            request = objectMapper.readValue(requestJson, PetProfileRequestDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format: " + e.getMessage());
        }

        // 파일이 있으면 S3에 업로드 후 URL을 요청 DTO에 반영
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = s3Service.upload(file);

        }
        PetProfileResponseDTO response = petProfileService.addPetProfile(userId, request,imageUrl);
        return ApiResponse.onSuccess(response);
    }

    /**
     * 반려동물 프로필 수정 API
     *
     * @param petId 반려동물 ID (PathVariable)
     * @return 수정된 반려동물 프로필 응답 DTO
     */
    @Operation(summary = "반려동물 프로필 수정", description = "사용자가 반려동물 프로필을 수정할 수 있는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필 수정 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 반려동물을 찾을 수 없음", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(mediaType = "application/json"))
    })
    @PutMapping(value = "/api/pets/{petId}", consumes = {"multipart/form-data"} )
    public ApiResponse<PetProfileResponseDTO> updatePetProfile(
            @PathVariable Long petId,
            @RequestPart(value = "request",required = true) String requestJson,
            @RequestPart(value = "file", required = false) MultipartFile file // 이미지 (선택)
    )throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        PetProfileRequestDTO request;
        try {
            request = objectMapper.readValue(requestJson, PetProfileRequestDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format: " + e.getMessage());
        }

        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = s3Service.upload(file);
        }
        PetProfileResponseDTO response = petProfileService.updatePetProfile(petId, request,imageUrl);
        return ApiResponse.onSuccess(response);
    }

    /**
     * 반려동물 프로필 삭제 API
     *
     * @param petId 반려동물 ID (PathVariable)
     * @return 삭제 성공 메시지
     */
    @Operation(summary = "반려동물 프로필 삭제", description = "사용자가 반려동물 프로필을 삭제할 수 있는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필 삭제 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 반려동물을 찾을 수 없음", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/api/pets/{petId}")
    public ApiResponse<String> deletePetProfile(
            @PathVariable Long petId
    ) {
        petProfileService.deletePetProfile(petId);
        return ApiResponse.onSuccess("반려동물 프로필이 성공적으로 삭제되었습니다.");
    }



    @Operation(
            summary = "사용자별 반려동물 목록 조회",
            description = "특정 userId에 대해 등록된 모든 반려동물 정보를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "반려동물 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PetProfileAllResponseDTO.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 사용자가 존재하지 않음",
                    content = @Content
            )
    })
    @GetMapping("/api/users/{userId}/pets/All")
    public ApiResponse<List<PetProfileAllResponseDTO>> getUserPets(
            @Parameter(
                    name = "userId",
                    description = "반려동물을 조회할 사용자 ID",
                    example = "1"
            )
            @PathVariable Long userId
    ) {
        // Service 호출 -> 해당 userId에 대한 반려동물 리스트를 가져옴
        List<PetProfileAllResponseDTO> petList = petProfileService.getPetsByUserId(userId);

        // ApiResponse로 감싸서 성공 응답
        return ApiResponse.onSuccess(petList);
    }


    @Operation(
            summary = "반려동물 단일 조회",
            description = "특정 사용자(userId)와 반려동물(petId)에 해당하는 반려동물 정보를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "반려동물 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PetProfileResponseDTO.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "반려동물을 찾을 수 없음",
                    content = @Content
            )
    })
    @GetMapping("/api/pets/{petId}")
    public ApiResponse<PetProfileResponseDTO> getPetProfile(
            @PathVariable Long petId   // 반려동물 ID
    ) {
        PetProfileResponseDTO response = petProfileService.getPetProfile(petId);
        return ApiResponse.onSuccess(response);
    }



}
