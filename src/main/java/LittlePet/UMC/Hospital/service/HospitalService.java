package LittlePet.UMC.Hospital.service;

import LittlePet.UMC.Hospital.dto.HospitalResponseDTO;
import LittlePet.UMC.Hospital.repository.HospitalPrefRepository;
import LittlePet.UMC.Hospital.repository.HospitalRepository;
import LittlePet.UMC.S3Service;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import LittlePet.UMC.Hospital.dto.HospitalRequestDTO;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final KakaoMapService kakaoMapService;
    private final HospitalPrefRepository hospitalPrefRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;


    public HospitalService(HospitalRepository hospitalRepository, HospitalPrefRepository hospitalPrefRepository, UserRepository userRepository, KakaoMapService kakaoMapService, S3Service s3Service){
        this.hospitalRepository = hospitalRepository;
        this.hospitalPrefRepository = hospitalPrefRepository;
        this.userRepository = userRepository;
        this.kakaoMapService = kakaoMapService;
        this.s3Service = s3Service;
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
                    return new HospitalResponseDTO(
                            hospital.getId(),
                            hospital.getName(),
                            hospital.getAddress(),
                            hospital.getPhoneNumber(),
                            hospital.getOpeningHours(),
                            hospital.getImageUrl(),
                            hospital.getRating()
                    );
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

    // 병원 정보 가져오기
    public HospitalRequestDTO getHospitalDetails(Long hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("병원을 찾을 수 없습니다."));
        return new HospitalRequestDTO(hospital);
    }

    @Transactional
    public List<HospitalRequestDTO> filterHospitals(String filterType) {
        List<Hospital> hospitals = hospitalRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        return hospitals.stream()
                .filter(hospital -> applyFilter(hospital, filterType, currentDate, currentTime))
                .map(HospitalRequestDTO::new)
                .collect(Collectors.toList());
    }

    //영업시간 필터링
    private boolean applyFilter(Hospital hospital, String filterType, LocalDate currentDate, LocalTime currentTime) {
        if (filterType == null) return true;

        switch (filterType) {
            case "주말":
                return isWeekendOpen(hospital);
            case "영업중":
                return isOpenNow(hospital, currentDate, currentTime);
            case "24시간":
                return isTwentyFourHours(hospital);
            default:
                return true;
        }
    }

    //주말 영업 병원
    private boolean isWeekendOpen(Hospital hospital) {
        String openingHours = hospital.getOpeningHours();
        return openingHours.contains("토") && openingHours.contains("일");
    }

    //현재 날짜+시간 기준 영업 중인 병원
    private boolean isOpenNow(Hospital hospital, LocalDate currentDate, LocalTime currentTime) {
        String openingHours = hospital.getOpeningHours();
        String dayOfWeek = currentDate.getDayOfWeek().toString();

        // 현재 요일에 맞는 영업시간 찾아오기
        String[] days = openingHours.split(",");
        for (String day : days) {
            String[] parts = day.split(" ");
            String dayName = parts[0]; // 예: "월"
            String timeRange = parts[1] + " " + parts[2]; // 예: "10:00 - 18:30"

            if (dayName.equalsIgnoreCase(dayOfWeek)) {
                // 요일이 일치하면, 영업시간 비교
                String[] times = timeRange.split(" - ");
                LocalTime openingTime = LocalTime.parse(times[0]);
                LocalTime closingTime = LocalTime.parse(times[1]);

                return !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime);
            }
        }
        return false; // 해당 요일에 영업시간이 없다면 false 반환
    }

    // 24시간 영업하는 병원
    private boolean isTwentyFourHours(Hospital hospital) {
        return hospital.getOpeningHours().contains("24:00");
    }
}
