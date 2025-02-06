package LittlePet.UMC.HealthRecord.controller;

import LittlePet.UMC.HealthRecord.dto.HealthRecordRequestDTO;
import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.HealthRecord.service.HealthRecordService;
import LittlePet.UMC.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pets/{petId}/health-records")
@RequiredArgsConstructor
public class HealthRecordController {

    private final HealthRecordService healthRecordService;

    /**
     * 특정 반려동물의 최신 건강 기록 조회
     */
    @Operation(summary = "최신 건강 기록 조회", description = "특정 반려동물의 최신 건강 기록을 반환합니다.")
    @GetMapping("/latest")
    public ApiResponse<HealthRecordResponseDTO> getLatestHealthRecord(@PathVariable Long petId) {
        HealthRecordResponseDTO response = healthRecordService.getLatestHealthRecord(petId);
        return ApiResponse.onSuccess(response);
    }

    /**
     * 특정 날짜 건강 기록 삭제
     */
    @Operation(summary = "특정 날짜 건강 기록 삭제", description = "특정 반려동물의 특정 날짜 건강 기록을 삭제합니다.")
    @DeleteMapping
    public ApiResponse<String> deleteHealthRecordByDate(
            @PathVariable Long petId,
            @RequestParam String localDate) {
        LocalDate date = LocalDate.parse(localDate);
        healthRecordService.deleteHealthRecordByDate(petId, date);
        return ApiResponse.onSuccess(localDate + " 건강 기록이 삭제되었습니다.");
    }

    /**
     * 특정 날짜의 건강 기록 조회
     */
    @Operation(summary = "특정 날짜 건강 기록 조회", description = "특정 반려동물의 특정 날짜 또는 전체 건강 기록을 반환합니다.")
    @GetMapping
    public ApiResponse<HealthRecordResponseDTO.HealthRecordDetailDTO> getHealthRecords(
            @PathVariable Long petId,
            @RequestParam(required = false) String localDate) {
        LocalDate date = localDate != null ? LocalDate.parse(localDate) : null;
        HealthRecordResponseDTO response = healthRecordService.getHealthRecords(petId, date);
        return ApiResponse.onSuccess(response.getLatestRecord());
    }

    /**
     * 건강 기록 등록 또는 수정
     */
    @Operation(summary = "건강 기록 등록 또는 수정", description = "특정 반려동물의 새로운 건강 기록을 등록하거나 기존 기록을 수정합니다.")
    @PostMapping
    public ApiResponse<HealthRecordResponseDTO> createOrUpdateHealthRecord(
            @PathVariable Long petId,
            @RequestBody @Valid HealthRecordRequestDTO request) {
        HealthRecordResponseDTO response = healthRecordService.createOrUpdateHealthRecord(petId, request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "건강 기록 날짜 조회", description = "특정 반려동물의 건강 기록이 있는 날짜를 반환합니다..")
    @GetMapping("/record-dates")
    public ApiResponse<List<LocalDate>> getRecordDates(@PathVariable Long petId) {
        List<LocalDate> recordDates = healthRecordService.getRecordDates(petId);
        return ApiResponse.onSuccess(recordDates);
    }
}
