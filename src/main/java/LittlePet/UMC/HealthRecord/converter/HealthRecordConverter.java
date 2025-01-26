package LittlePet.UMC.HealthRecord.converter;

import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;

public class HealthRecordConverter {

    public static HealthRecordResponseDTO toHealthRecordResponseDTO(UserPet pet, String recentUpdate, HealthRecord latestRecord) {
        return HealthRecordResponseDTO.builder()
                .petName(pet.getName())
                .profilePhoto(pet.getProfilePhoto())
                .birthDay(pet.getBirthDay() != null ? pet.getBirthDay().toString() : null)
                .recentUpdate(recentUpdate)
                .latestRecord(latestRecord != null ? toHealthRecordDetailDTO(latestRecord) : null)
                .build();
    }

    // HealthRecord를 DTO로 변환하는 메서드
    private static HealthRecordResponseDTO.HealthRecordDetailDTO toHealthRecordDetailDTO(HealthRecord healthRecord) {
        return HealthRecordResponseDTO.HealthRecordDetailDTO.builder()
                .recordDate(healthRecord.getRecordDate().toString())
                .weight(healthRecord.getWeight())
                .mealAmount(healthRecord.getMealAmount().toString())
                .fecesStatus(healthRecord.getFecesStatus().toString())
                .healthStatus(healthRecord.getHealthStatus().toString())
                .abnormalSymptoms(healthRecord.getAbnormalSymptoms())
                .diagnosisName(healthRecord.getDiagnosisName())
                .prescription(healthRecord.getPrescription())
                .build();
    }
}
