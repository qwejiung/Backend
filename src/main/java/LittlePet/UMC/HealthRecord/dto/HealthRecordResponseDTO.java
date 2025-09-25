package LittlePet.UMC.HealthRecord.dto;

import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HealthRecordResponseDTO {

    private String petName;
    private String profilePhoto;
    private String birthDay;
    private String gender;
    private String petCategory;
    private String recentUpdate; // 최근 업데이트 텍스트
    private HealthRecordDetailDTO latestRecord; // 최신 건강 기록의 세부 정보
    private Double weightDifference; // 체중 변화량 추가

    @Getter
    @Builder
    public static class HealthRecordDetailDTO {
        private String recordDate;
        private Double weight;
        private String mealAmount;
        private String healthStatus;
        private String atypicalSymptom;
        private String otherSymptom;
        private String diagnosisName;
        private String prescription;
    }
}
