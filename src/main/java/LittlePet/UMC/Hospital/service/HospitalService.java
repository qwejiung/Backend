package LittlePet.UMC.Hospital.service;

import LittlePet.UMC.Hospital.dto.HospitalResponseDTO;
import LittlePet.UMC.Hospital.repository.HospitalPrefRepository;
import LittlePet.UMC.Hospital.repository.HospitalRepository;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import LittlePet.UMC.Hospital.dto.HospitalRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final KakaoMapService kakaoMapService;
    private final HospitalPrefRepository hospitalPrefRepository;
    private final UserRepository userRepository;


    public HospitalService(HospitalRepository hospitalRepository, HospitalPrefRepository hospitalPrefRepository, UserRepository userRepository, KakaoMapService kakaoMapService){
        this.hospitalRepository = hospitalRepository;
        this.hospitalPrefRepository = hospitalPrefRepository;
        this.userRepository = userRepository;
        this.kakaoMapService = kakaoMapService;
    }

    @Transactional
    public void scrapHospital(Long userId, Long hospitalId){

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("사용자가 없습니다."));
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(()-> new RuntimeException("없는 병원입니다."));

        if (hospitalPrefRepository.existsByUserAndHospital(user, hospital)) {
            throw new RuntimeException("이미 스크랩한 병원입니다.");
        }

        HospitalPref hospitalPref = HospitalPref.builder()
                .user(user)
                .hospital(hospital)
                .build();

        hospital.addHospitalPref(hospitalPref);

        hospitalPrefRepository.save(hospitalPref);
    }

    @Transactional
    public void cancelScrapHospital(Long userId, Long hospitalId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자가 없습니다."));
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new RuntimeException("없는 병원입니다."));

        HospitalPref hospitalPref = hospitalPrefRepository.findByUserAndHospital(user, hospital)
                .orElseThrow(() -> new RuntimeException("스크랩한 병원이 아닙니다."));

        hospitalPrefRepository.delete(hospitalPref);
    }

    public List<HospitalResponseDTO> getScrapedHospitals(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        return hospitalPrefRepository.findByUser(user).stream()
                .map(hospitalPref -> {
                    Hospital hospital = hospitalPref.getHospital();
                    return new HospitalResponseDTO(hospital.getId(), hospital.getName(), hospital.getAddress());
                })
                .collect(Collectors.toList());
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
