package LittlePet.UMC.Hospital.controller;

import LittlePet.UMC.Hospital.service.KakaoMapService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoMapController {
    private final KakaoMapService kakaoMapService;

    public KakaoMapController(KakaoMapService kakaoMapService) {
        this.kakaoMapService = kakaoMapService;
    }

    @Operation(summary = "카카오 API를 통해 위도/경도 조회", description = "병원의 주소를 입력하면 위도/경도가 반환됩니다.")
    @GetMapping("/api/getCoordinates")
    public String[] getCoordinates(@RequestParam String address) {
        return kakaoMapService.getCoordinates(address);
    }
}
