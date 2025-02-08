package LittlePet.UMC.Hospital.controller;

import LittlePet.UMC.Hospital.dto.HospitalRequestDTO;
import LittlePet.UMC.Hospital.dto.HospitalResponseDTO;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.Hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "병원 API", description = "병원 스크랩 및 스크랩 취소 API")
public class HospitalController {

    private final HospitalService hospitalService;

    @Operation(summary = "병원 스크랩", description = "특정 병원을 스크랩하여 저장합니다.")
    @PostMapping("/hospitals/{hospital-id}")
    public ApiResponse<String> scrapHospital(
            @PathVariable("hospital-id") Long hospitalId,
            @RequestParam("userId") Long userId) {

        hospitalService.scrapHospital(userId, hospitalId);

        return ApiResponse.onSuccess("병원이 내 스크랩에 저장되었습니다.");
    }

    @Operation(summary = "병원 스크랩 취소", description = "특정 병원의 스크랩을 취소합니다.")
    @DeleteMapping("/hospitals/{hospital-id}")
    public ApiResponse<String> cancelScrapHospital(
            @PathVariable("hospital-id") Long hospitalId,
            @RequestParam("userId") Long userId) {

        hospitalService.cancelScrapHospital(userId, hospitalId);

        return ApiResponse.onSuccess("병원 스크랩이 취소되었습니다.");
    }

    @Operation(summary = "스크랩한 병원 조회", description = "특정 유저가 스크랩한 병원 목록을 반환합니다.")
    @GetMapping("/hospitals/users/{userId}")
    public ApiResponse<List<HospitalResponseDTO>> getScrapedHospitals(
            @RequestParam("userId") Long userId) {

        List<HospitalResponseDTO> scrapedHospitals = hospitalService.getScrapedHospitals(userId);
        return ApiResponse.onSuccess(scrapedHospitals);
    }
}
