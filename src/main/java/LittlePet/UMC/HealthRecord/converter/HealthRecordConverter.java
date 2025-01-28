package LittlePet.UMC.HealthRecord.converter;

import LittlePet.UMC.HealthRecord.dto.HealthRecordRequestDTO;
import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.domain.enums.*;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Collectors;

public class HealthRecordConverter {

    /**
     * HealthRecordRequestDTO를 HealthRecord Entity로 변환
     */
        public static HealthRecord toHealthRecordEntity(HealthRecordRequestDTO request, UserPet pet) {
            return HealthRecord.builder()
                    .recordDate(LocalDate.parse(request.getRecordDate()))
                    .weight(request.getWeight())
                    .mealAmount(MealAmountEnum.fromDescription(request.getMealAmount().toUpperCase()))
                    .fecesStatus(FecesStatusEnum.fromDescription(request.getFecesStatus().toUpperCase()))
                    .fecesColorStatus(FecesColorStatusEnum.fromDescription(request.getFecesColorStatus().toUpperCase()))
                    .healthStatus(HealthStatusEnum.fromDescription(request.getHealthStatus()))
                    .atypicalSymptom(
                            request.getAtypicalSymptom() == null
                                    ? Collections.emptyList()
                                    : request.getAtypicalSymptom().stream()
                                    // 예: "기침" -> AtypicalSymptomEnum.fromDescription("기침") 사용
                                    .map(AtypicalSymptomEnum::fromDescription)
                                    .collect(Collectors.toList())
                    )
                    .diagnosisName(request.getDiagnosisName())
                    .prescription(request.getPrescription())
                    .userPet(pet)
                    .build();
        }


    /**
     * UserPet 객체와 HealthRecord 객체를 바탕으로 HealthRecordResponseDTO를 생성하는 메서드
     *
     * @param pet         UserPet 객체 (반려동물 정보)
     * @param recentUpdate 최근 업데이트 상태 ("오늘", "어제", "2일 전" 등)
     * @param latestRecord 최신 HealthRecord 객체
     * @return HealthRecordResponseDTO
     */
    public static HealthRecordResponseDTO toHealthRecordResponseDTO(UserPet pet, String recentUpdate, HealthRecord latestRecord) {
        return HealthRecordResponseDTO.builder()
                .petName(pet.getName()) // 반려동물 이름
                .profilePhoto(pet.getProfilePhoto()) // 반려동물 프로필 사진
                .birthDay(pet.getBirthDay() != null ? pet.getBirthDay().toString() : null) // 반려동물 생년월일
                .recentUpdate(recentUpdate) // 최근 업데이트 정보
                .latestRecord(latestRecord != null ? toHealthRecordDetailDTO(latestRecord) : null) // 최신 건강 기록 정보
                .build();
    }

    /**
     * HealthRecord 객체를 세부 DTO로 변환하는 메서드
     *
     * @param healthRecord HealthRecord 객체
     * @return HealthRecordDetailDTO
     */
    private static HealthRecordResponseDTO.HealthRecordDetailDTO toHealthRecordDetailDTO(HealthRecord healthRecord) {
        MealAmountEnum mealAmount = healthRecord.getMealAmount();
        FecesStatusEnum fecesStatus = healthRecord.getFecesStatus();
        FecesColorStatusEnum fecesColor = healthRecord.getFecesColorStatus();
        HealthStatusEnum healthStatus = healthRecord.getHealthStatus();

        // AtypicalSymptom (List<Enum>) -> List<String> (한글) 변환 예시
        var atypicalList = healthRecord.getAtypicalSymptom() == null
                ? Collections.<String>emptyList()
                : healthRecord.getAtypicalSymptom().stream()
                .map(AtypicalSymptomEnum::getDescription) // 예: COUGH -> "기침"
                .collect(Collectors.toList());

        return HealthRecordResponseDTO.HealthRecordDetailDTO.builder()
                .recordDate(healthRecord.getRecordDate().toString())
                .weight(healthRecord.getWeight())
                // 한글로 응답할 때는 getDescription() 사용
                .mealAmount(mealAmount != null ? mealAmount.getDescription() : null)
                .fecesStatus(fecesStatus != null ? fecesStatus.getDescription() : null)
                .fecesColorStatus(fecesColor != null ? fecesColor.getDescription() : null)
                .healthStatus(healthStatus != null ? healthStatus.getDescription() : null)
                // 여러 증상이면 DTO 필드에도 List<String>으로 만들어 두시면 됩니다.
                .atypicalSymptom(atypicalList)
                .diagnosisName(healthRecord.getDiagnosisName())
                .prescription(healthRecord.getPrescription())
                .build();
    }
}
