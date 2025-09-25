package LittlePet.UMC.HealthRecord.service;

import LittlePet.UMC.HealthRecord.converter.HealthRecordConverter;
import LittlePet.UMC.HealthRecord.dto.HealthRecordDateResponseDTO;
import LittlePet.UMC.HealthRecord.dto.HealthRecordRequestDTO;
import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.HealthRecord.repository.HealthRecordRepository;
import LittlePet.UMC.User.repository.UserPetRepository;
import LittlePet.UMC.domain.enums.*;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
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

        //Optional<HealthRecord> latestRecord = healthRecordRepository.findFirstByUserPetOrderByRecordDateDesc(pet);

        Optional<HealthRecord> latestRecord = healthRecordRepository.findFirstByUserPetOrderByRecordDateDesc(pet);

        // 이전 기록
        Optional<HealthRecord> previousRecord = latestRecord.flatMap(record ->
                healthRecordRepository.findFirstByUserPetAndRecordDateBeforeOrderByRecordDateDesc(pet, record.getRecordDate())
        );

        // 체중 변화 계산 (최신 체중 - 이전 체중)
        Double weightDifference = null;
        if (latestRecord.isPresent() && previousRecord.isPresent()) {
            weightDifference = Math.round((latestRecord.get().getWeight() - previousRecord.get().getWeight()) * 10.0) / 10.0;
        }

        String recentUpdate = latestRecord
                .map(record -> calculateRecentUpdate(record.getRecordDate()))
                .orElse("최근 업데이트 없음");

        //return HealthRecordConverter.toHealthRecordResponseDTO(pet, recentUpdate, latestRecord.orElse(null));

        return HealthRecordResponseDTO.builder()
                .petName(pet.getName())
                .profilePhoto(pet.getProfilePhoto())
                .birthDay(pet.getBirthDay() != null ? pet.getBirthDay().toString() : null)
                .gender(pet.getGender().toString())
                .petCategory(pet.getPetCategory().getSpecies() != null ? pet.getPetCategory().getSpecies() : null)
                .weightDifference(weightDifference)
                .recentUpdate(recentUpdate)
                .latestRecord(latestRecord.map(record -> HealthRecordConverter.toHealthRecordDetailDTO(record)).orElse(null))
                .build();

    }



    /**
     * 특정 날짜 또는 모든 건강 기록 조회
     */
    public HealthRecordDateResponseDTO getHealthRecords(Long petId, LocalDate localDate) {
        UserPet pet = userPetRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물을 찾을 수 없습니다."));

        Optional<HealthRecord> record = (localDate != null)
                ? healthRecordRepository.findByUserPetAndRecordDate(pet, localDate)
                : healthRecordRepository.findFirstByUserPetOrderByRecordDateDesc(pet);

        String recentUpdate = record
                .map(r -> calculateRecentUpdate(r.getRecordDate()))
                .orElse("최근 업데이트 없음");
        if (record.isEmpty()) {
            throw new IllegalArgumentException("해당 날짜의 건강 기록을 찾을 수 없습니다.");
        }

        // ✅ 최신 기록
        HealthRecord latestRecord = record.get();

        // ✅ 이전 기록 조회 (latestRecord보다 과거의 기록 중 가장 최신)
        Optional<HealthRecord> previousRecord = healthRecordRepository
                .findFirstByUserPetAndRecordDateBeforeOrderByRecordDateDesc(pet, latestRecord.getRecordDate());

        // ✅ 체중 변화 계산 (소수점 1자리까지 반올림 후 Double 타입 유지)
        Double weightDifference = previousRecord
                .map(prev -> BigDecimal.valueOf(latestRecord.getWeight() - prev.getWeight())
                        .setScale(1, RoundingMode.HALF_UP) // 소수점 1자리까지 반올림
                        .doubleValue()) // Double 타입으로 변환
                .orElse(null);

        return HealthRecordConverter.toHealthRecordResponseDateDTO(pet, latestRecord, weightDifference);

    }

    // ✅ 특정 날짜의 건강 기록 삭제
    @Transactional
    public void deleteHealthRecordByDate(Long petId, LocalDate recordDate) {
        UserPet pet = userPetRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물을 찾을 수 없습니다."));

        HealthRecord record = healthRecordRepository.findByUserPetAndRecordDate(pet, recordDate)
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 건강 기록을 찾을 수 없습니다."));

        healthRecordRepository.delete(record);
    }

    /**
     * 새로운 건강 기록 등록 또는 수정
     */
    @Transactional
    public HealthRecordResponseDTO createOrUpdateHealthRecord(Long petId, HealthRecordRequestDTO request) {
        UserPet pet = userPetRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물을 찾을 수 없습니다."));

        // 1) 새로 들어온 DTO -> Entity 변환(기본적으로는 "새 레코드"라고 가정)
        HealthRecord newRecord = HealthRecordConverter.toHealthRecordEntity(request, pet);

        // 2) 날짜로 기존 레코드 존재 여부 확인
        LocalDate recordDate = LocalDate.parse(request.getRecordDate());
        Optional<HealthRecord> existingRecord = healthRecordRepository.findByUserPetAndRecordDate(pet, recordDate);

        // 3) 기존 레코드가 있으면 → 그 레코드 ID를 재사용하여 '업데이트용' 엔티티 빌드
        HealthRecord finalRecord = existingRecord
                .map(oldRecord ->
                        // 빌더로 새 인스턴스를 만들지만, id만 oldRecord의 id를 사용
                        HealthRecord.builder()
                                .id(oldRecord.getId())                 // 기존 ID 유지
                                .recordDate(newRecord.getRecordDate()) // 이하 필드는 새로 들어온 newRecord 값들 그대로
                                .weight(newRecord.getWeight())
                                .mealAmount(newRecord.getMealAmount())
                                .fecesStatus(newRecord.getFecesStatus())
                                .fecesColorStatus(newRecord.getFecesColorStatus())
                                .healthStatus(newRecord.getHealthStatus())
                                .atypicalSymptom(newRecord.getAtypicalSymptom())
                                .otherSymptom(newRecord.getOtherSymptom())
                                .diagnosisName(newRecord.getDiagnosisName())
                                .prescription(newRecord.getPrescription())
                                .userPet(newRecord.getUserPet())
                                .build()
                )
                // 4) 기존 레코드가 없으면 그대로 newRecord 사용
                .orElse(newRecord);

        // 5) 저장
        HealthRecord savedRecord = healthRecordRepository.save(finalRecord);

        // 6) 응답 DTO 변환
        String recentUpdate = calculateRecentUpdate(savedRecord.getRecordDate());
        return HealthRecordConverter.toHealthRecordResponseDTO(pet, recentUpdate, savedRecord);
    }

    /**
     * 해당 반려동물의 모든 기록 날짜 목록
     */
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
