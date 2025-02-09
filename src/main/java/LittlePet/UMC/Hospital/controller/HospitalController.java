package LittlePet.UMC.Hospital.controller;

import LittlePet.UMC.Hospital.dto.HospitalRequestDTO;
import LittlePet.UMC.Hospital.dto.HospitalResponseDTO;
import lombok.RequiredArgsConstructor;
import LittlePet.UMC.Hospital.service.HospitalService;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "hospital-controller", description = "지역별로 병원 조회 및 병원 스크랩 API")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

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

    @Operation(summary = "지역별 병원 조회", description = "특정 지역별로 병원을 조회합니다.")
    @GetMapping("/hospitals/search/{placeId}")
    public ApiResponse<List<HospitalRequestDTO>> searchHospitals(@RequestParam Long placeId) {
        List<HospitalRequestDTO> hospitals = hospitalService.searchHospitals(placeId);
        return ApiResponse.onSuccess(hospitals);
    }

    @Operation(summary = "병원 상세 조회", description = "병원 정보를 조회합니다.")
    @GetMapping("/hospitals/{hospitalId}")
    public ApiResponse<HospitalRequestDTO> getHospitalDetails(@PathVariable Long hospitalId) {
        HospitalRequestDTO hospitalDetails = hospitalService.getHospitalDetails(hospitalId);
        return ApiResponse.onSuccess(hospitalDetails);
    }
}
