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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
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
    @Transactional
    private boolean isWeekendOpen(Hospital hospital) {
        String openingHours = hospital.getOpeningHours();
        return openingHours.contains("토") && openingHours.contains("일");
    }

    //현재 날짜+시간 기준 영업 중인 병원
    @Transactional
    public boolean isOpenNow(Hospital hospital, LocalDate currentDate, LocalTime currentTime) {

        String openingHours = hospital.getOpeningHours();
        String[] days = openingHours.split("\n"); // 줄바꿈 기준으로 나누기

        String koreanDay = currentDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        for (String day : days) {
            String[] parts = day.split("\\s", 2); // 공백 기준으로 요일과 시간 분리
            if (parts.length < 2) continue;

            String dayName = parts[0].trim(); // "월"
            if (!dayName.equals(koreanDay)) continue;

            if (parts[1].contains("휴무")) {
                return false;
            }

            String[] times = parts[1].trim().split("\\s*-\\s*");
            if (times.length < 2) continue;

            try {
                LocalTime openingTime = LocalTime.parse(times[0].trim());
                LocalTime closingTime = LocalTime.parse(times[1].trim());

                boolean isOpen = !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime);
                return isOpen;
            } catch (Exception e) {
                continue; // 시간 파싱 오류가 있을 경우, 다른 요일로 넘어감
            }
        }
        return false;
    }


    // 24시간 영업하는 병원
    @Transactional
    private boolean isTwentyFourHours(Hospital hospital) {
        String openingHours = hospital.getOpeningHours();
        return openingHours.contains("00:00") && hospital.getOpeningHours().contains("23:59");
    }

}
