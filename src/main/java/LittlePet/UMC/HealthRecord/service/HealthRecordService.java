package LittlePet.UMC.HealthRecord.service;

import LittlePet.UMC.HealthRecord.converter.HealthRecordConverter;
import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.HealthRecord.repository.HealthRecordRepository;
import LittlePet.UMC.User.repository.UserPetRepository;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthRecordService {
    private final UserPetRepository userPetRepository;
    private final HealthRecordRepository healthRecordRepository;

    public HealthRecordResponseDTO getPetHealthRecords(Long userId, Long petId) {
        // 반려동물 확인
        UserPet pet = userPetRepository.findByIdAndUserId(petId, userId)
                .orElseThrow(() -> new IllegalArgumentException("반려동물을 찾을 수 없습니다."));

        // 건강 기록 가져오기
        List<HealthRecord> records = healthRecordRepository.findByUserPet(pet);

        // 최신 건강 기록 가져오기
        Optional<HealthRecord> latestRecordOpt = records.stream()
                .max(Comparator.comparing(HealthRecord::getRecordDate));

        // 최근 업데이트 텍스트 계산
        String recentUpdate = latestRecordOpt
                .map(record -> calculateRecentUpdate(record.getRecordDate()))
                .orElse("아직 작성되지 않았습니다.");

        // DTO 변환
        return HealthRecordConverter.toHealthRecordResponseDTO(pet, recentUpdate, latestRecordOpt.orElse(null));
    }

    private String calculateRecentUpdate(LocalDate recordDate) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(recordDate, today);

        if (period.getDays() == 0) {
            return "오늘";
        } else if (period.getDays() == 1) {
            return "어제";
        } else if (period.getDays() <= 30) {
            return period.getDays() + "일 전";
        } else {
            return recordDate.toString();
        }
    }
}
