package LittlePet.UMC.HealthRecord.converter;

import LittlePet.UMC.HealthRecord.dto.HealthRecordRequestDTO;
import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.domain.enums.FecesColorStatusEnum;
import LittlePet.UMC.domain.enums.FecesStatusEnum;
import LittlePet.UMC.domain.enums.HealthStatusEnum;
import LittlePet.UMC.domain.enums.MealAmountEnum;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;

import java.time.LocalDate;

public class HealthRecordConverter {

    /**
     * HealthRecordRequestDTO를 HealthRecord Entity로 변환
     */
        public static HealthRecord toHealthRecordEntity(HealthRecordRequestDTO request, UserPet pet) {
            return HealthRecord.builder()
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
        return HealthRecordResponseDTO.HealthRecordDetailDTO.builder()
                .recordDate(healthRecord.getRecordDate().toString()) // 기록 날짜
                .weight(healthRecord.getWeight()) // 체중
                .mealAmount(healthRecord.getMealAmount().toString()) // 식사량
                .fecesStatus(healthRecord.getFecesStatus().toString()) // 대변 상태
                .healthStatus(healthRecord.getHealthStatus().toString()) // 건강 상태
                .abnormalSymptoms(healthRecord.getAbnormalSymptoms()) // 이상 증상
                .diagnosisName(healthRecord.getDiagnosisName()) // 진단명
                .prescription(healthRecord.getPrescription()) // 처방 내역
                .build();
    }
}
