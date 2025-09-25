package LittlePet.UMC.Hospital.controller;

import LittlePet.UMC.Hospital.dto.HospitalRequestDTO;
import LittlePet.UMC.Hospital.dto.HospitalResponseDTO;
import LittlePet.UMC.S3Service;
import lombok.RequiredArgsConstructor;
import LittlePet.UMC.Hospital.service.HospitalService;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "hospital-controller", description = "지역별로 병원 조회 및 병원 스크랩 API")
public class HospitalController {

    private final HospitalService hospitalService;
    private final S3Service s3Service;

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

    @Operation(summary = "S3에 이미지 업로드 후 URL 반환", description = "이미지를 S3에 업로드하고 URL을 반환합니다.")
    @PostMapping(value = "/images/upload", consumes = "multipart/form-data", produces = "application/json")
    public ApiResponse<String> uploadImage(
            @RequestParam("file") MultipartFile file) {
        try {
            // S3에 파일 업로드하고 URL 반환
            String fileUrl = s3Service.upload(file);
            return ApiResponse.onSuccess("이미지 URL: " + fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.onFailure("COMMON500","업로드 실패", e.getMessage());
        }
    }

    @Operation(summary = "병원 필터링해서 조회", description = "현재 영업중, 주말 영업, 24시간 영업하는 병원 별로 조회합니다.")
    @GetMapping("/hospitals/filter")
    public ApiResponse<List<HospitalRequestDTO>> filterHospitals(@RequestParam(required = false) String filterType) {
        return ApiResponse.onSuccess(hospitalService.filterHospitals(filterType));
    }

}
