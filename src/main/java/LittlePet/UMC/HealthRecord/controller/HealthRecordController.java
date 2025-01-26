package LittlePet.UMC.HealthRecord.controller;

import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.HealthRecord.service.HealthRecordService;
import LittlePet.UMC.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/pets/{petId}/health-records")
@RequiredArgsConstructor
public class HealthRecordController {
    private final HealthRecordService healthRecordService;

    @Operation(summary = "특정 반려동물 건강 기록 조회", description = "특정 사용자와 반려동물의 건강 기록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "반려동물을 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping
    public ApiResponse<HealthRecordResponseDTO> getHealthRecords(
            @PathVariable Long userId,
            @PathVariable Long petId
    ) {
        HealthRecordResponseDTO response = healthRecordService.getPetHealthRecords(userId, petId);
        return ApiResponse.onSuccess(response);
    }
}
