package LittlePet.UMC.Hospital.service;

import LittlePet.UMC.Hospital.dto.HospitalRequestDTO;
import LittlePet.UMC.Hospital.repository.HospitalRepository;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final KakaoMapService kakaoMapService;

    public HospitalService(HospitalRepository hospitalRepository, KakaoMapService kakaoMapService) {
        this.hospitalRepository = hospitalRepository;
        this.kakaoMapService = kakaoMapService;
    }

    @Transactional
    public List<HospitalRequestDTO> searchHospitals(Long placeId) {
        List<Hospital> hospitals = hospitalRepository.findHospitalsByPlaceId(placeId);

        return hospitals.stream().map(hospital -> {
            // 병원 데이터에 위도/경도가 없으면 카카오 API 호출하여 실시간으로 위도/경도 얻기
            if (hospital.getLatitude() == null || hospital.getLongitude() == null) {
                String[] coordinates = kakaoMapService.getCoordinates(hospital.getAddress());

                // 위도/경도가 성공적으로 반환된 경우에만 업데이트
                if (coordinates[0] != null && coordinates[1] != null) {
                    hospital.updateCoordinates(coordinates[0], coordinates[1]);  // 위도/경도 업데이트
                    hospitalRepository.save(hospital);
                }
            }
            return new HospitalRequestDTO(hospital);
        }).collect(Collectors.toList());
    }

    public HospitalRequestDTO getHospitalDetails(Long hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("병원을 찾을 수 없습니다."));
        return new HospitalRequestDTO(hospital);
    }
}
