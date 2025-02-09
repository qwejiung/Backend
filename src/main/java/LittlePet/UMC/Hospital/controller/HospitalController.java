package LittlePet.UMC.Hospital.controller;

import LittlePet.UMC.Hospital.dto.HospitalRequestDTO;
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
@Tag(name = "hospital-controller", description = "지역별로 병원 조회 및 병원 스크랩 API")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
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
