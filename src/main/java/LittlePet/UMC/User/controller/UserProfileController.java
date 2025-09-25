package LittlePet.UMC.User.controller;

import LittlePet.UMC.S3Service;
import LittlePet.UMC.User.dto.PetProfileRequest.PetProfileRequestDTO;
import LittlePet.UMC.User.dto.UserRequest.UserProfileRequestDTO;
import LittlePet.UMC.User.dto.UserResponse.UserProfileResponseDTO;
import LittlePet.UMC.User.dto.UserResponse.UserUpdateProfileResponseDTO;
import LittlePet.UMC.User.service.UserProfileService;
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

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final S3Service s3Service;

    @Operation(summary = "사용자 프로필 조회", description = "userId에 해당하는 사용자 프로필을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "사용자 프로필 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/{userId}")
    public ApiResponse<UserProfileResponseDTO> getUserProfile(@PathVariable Long userId) {
        UserProfileResponseDTO response = userProfileService.getUserProfile(userId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "프로필 수정", description = "사용자의 프로필 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 프로필이 수정되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다. 중복된 닉네임 또는 유효하지 않은 입력값"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.")
    })
    @PostMapping(value = "/{userId}",consumes = {"multipart/form-data"})
    public ApiResponse<UserUpdateProfileResponseDTO> updateProfile(
            @PathVariable Long userId,
            @RequestPart(value = "request", required = true) String requestJson, // JSON 요청 문자열
            @RequestPart(value = "file", required = false) MultipartFile file // 이미지 (선택)
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        UserProfileRequestDTO request;
        try {
            request = objectMapper.readValue(requestJson, UserProfileRequestDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format: " + e.getMessage());
        }

        String ImageUrl = null;
        if (file != null && !file.isEmpty()) {
            ImageUrl = s3Service.upload(file);
            System.out.println("Uploaded Image URL: " + ImageUrl);
        }
        UserUpdateProfileResponseDTO response = userProfileService.updateProfile(userId, request,ImageUrl);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "프로필 조회 ", description = "사용자의 프로필 정보를 조회 합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 프로필이 조회되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다. 중복된 닉네임 또는 유효하지 않은 입력값"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.")
    })
    @GetMapping(value = "/update/{userId}")
    public ApiResponse<UserUpdateProfileResponseDTO> updateUserProfile(
            @PathVariable Long userId
    ){
        UserUpdateProfileResponseDTO response = userProfileService.getUserIdProfile(userId);
        return ApiResponse.onSuccess(response);
    }

}
