package LittlePet.UMC.HealthRecord.service;

import LittlePet.UMC.HealthRecord.converter.HealthRecordConverter;
import LittlePet.UMC.HealthRecord.dto.HealthRecordRequestDTO;
import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.HealthRecord.repository.HealthRecordRepository;
import LittlePet.UMC.User.repository.UserPetRepository;
import LittlePet.UMC.domain.enums.FecesColorStatusEnum;
import LittlePet.UMC.domain.enums.FecesStatusEnum;
import LittlePet.UMC.domain.enums.HealthStatusEnum;
import LittlePet.UMC.domain.enums.MealAmountEnum;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthRecordService {
    private final UserPetRepository userPetRepository;
    private final HealthRecordRepository healthRecordRepository;

    /**
     * 특정 반려동물의 최신 건강 기록 조회
     */
    public HealthRecordResponseDTO getLatestHealthRecord(Long petId) {
        UserPet pet = userPetRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물을 찾을 수 없습니다."));

        Optional<HealthRecord> latestRecord = healthRecordRepository.findFirstByUserPetOrderByRecordDateDesc(pet);

        String recentUpdate = latestRecord
                .map(record -> calculateRecentUpdate(record.getRecordDate()))
                .orElse("최근 업데이트 없음");

        return HealthRecordConverter.toHealthRecordResponseDTO(pet, recentUpdate, latestRecord.orElse(null));
    }

    /**
     * 특정 날짜 또는 모든 건강 기록 조회
     */
    public HealthRecordResponseDTO getHealthRecords(Long petId, LocalDate localDate) {
        UserPet pet = userPetRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물을 찾을 수 없습니다."));

        Optional<HealthRecord> record = (localDate != null)
                ? healthRecordRepository.findByUserPetAndRecordDate(pet, localDate)
                : healthRecordRepository.findFirstByUserPetOrderByRecordDateDesc(pet);

        String recentUpdate = record
                .map(r -> calculateRecentUpdate(r.getRecordDate()))
                .orElse("최근 업데이트 없음");

        return HealthRecordConverter.toHealthRecordResponseDTO(pet, recentUpdate, record.orElse(null));
    }

    /**
     * 새로운 건강 기록 등록 또는 수정
     */
    public HealthRecordResponseDTO createOrUpdateHealthRecord(Long petId, HealthRecordRequestDTO request) {
        UserPet pet = userPetRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("반려동물을 찾을 수 없습니다."));

        // 기존 기록 조회
        Optional<HealthRecord> existingRecord = healthRecordRepository.findByUserPetAndRecordDate(pet, LocalDate.parse(request.getRecordDate()));

        // 빌더 패턴으로 새 객체 생성
        HealthRecord updatedRecord = HealthRecord.builder()
                .id(existingRecord.map(HealthRecord::getId).orElse(null)) // 기존 ID 유지
                .recordDate(LocalDate.parse(request.getRecordDate()))
                .weight(request.getWeight())
                .mealAmount(MealAmountEnum.valueOf(request.getMealAmount().toUpperCase()))
                .fecesStatus(FecesStatusEnum.valueOf(request.getFecesStatus().toUpperCase()))
                .fecesColorStatus(FecesColorStatusEnum.valueOf(request.getFecesColorStatus().toUpperCase()))
                .healthStatus(HealthStatusEnum.valueOf(request.getHealthStatus().toUpperCase()))
                .abnormalSymptoms(request.getAbnormalSymptoms())
                .diagnosisName(request.getDiagnosisName())
                .prescription(request.getPrescription())
                .userPet(pet)
                .build();

        // 저장
        healthRecordRepository.save(updatedRecord);

        // 응답 반환
        String recentUpdate = calculateRecentUpdate(updatedRecord.getRecordDate());
        return HealthRecordConverter.toHealthRecordResponseDTO(pet, recentUpdate, updatedRecord);
    }

    public List<LocalDate> getRecordDates(Long petId) {
        UserPet pet = userPetRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("반려동물을 찾을 수 없습니다."));

        return healthRecordRepository.findAllByUserPet(pet).stream()
                .map(HealthRecord::getRecordDate) // 기록 날짜만 추출
                .collect(Collectors.toList());
    }

    /**
     * 최근 업데이트 계산
     */
    private String calculateRecentUpdate(LocalDate recordDate) {
        LocalDate today = LocalDate.now();
        long daysDifference = today.toEpochDay() - recordDate.toEpochDay();

        if (daysDifference == 0) {
            return "오늘";
        } else if (daysDifference == 1) {
            return "어제";
        } else if (daysDifference <= 30) {
            return daysDifference + "일 전";
        } else {
            return "30일 이상 전";
        }
    }
}
