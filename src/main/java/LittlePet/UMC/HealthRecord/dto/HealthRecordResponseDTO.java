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
    private String recentUpdate; // 최근 업데이트 텍스트
    private HealthRecordDetailDTO latestRecord; // 최신 건강 기록의 세부 정보

    @Getter
    @Builder
    public static class HealthRecordDetailDTO {
        private String recordDate;
        private Double weight;
        private String mealAmount;
        private String fecesStatus;
        private String fecesColorStatus;
        private String healthStatus;
        private List<String> atypicalSymptom;
        private String diagnosisName;
        private String prescription;
    }
}
