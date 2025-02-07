package LittlePet.UMC.Hospital.controller;

import LittlePet.UMC.Hospital.service.KakaoMapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoMapController {
    private final KakaoMapService kakaoMapService;

    public KakaoMapController(KakaoMapService kakaoMapService) {
        this.kakaoMapService = kakaoMapService;
    }

    @GetMapping("/api/getCoordinates")
    public String getCoordinates(@RequestParam String address) {
        return kakaoMapService.getCoordinates(address);
    }
}
