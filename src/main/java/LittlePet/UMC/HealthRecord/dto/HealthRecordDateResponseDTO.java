package LittlePet.UMC.HealthRecord.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthRecordDateResponseDTO {

    private String recordDate;
    private String petName;
    private Double weight;
    private String mealAmount;
    private String fecesStatus;
    private String fecesColorStatus;
    private String fecesStatusProfile;
    private String healthStatus;
    private String atypicalSymptom;
    private String diagnosisName;
    private String prescription;
    private Double weightDifference; // 체중 변화량 추가

}
