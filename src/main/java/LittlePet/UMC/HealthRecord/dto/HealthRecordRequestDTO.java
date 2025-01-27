package LittlePet.UMC.HealthRecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthRecordRequestDTO {
    private String recordDate; // 날짜 (YYYY-MM-DD)
    private Double weight;
    private String mealAmount;
    private String fecesStatus;
    private String fecesType;
    private String fecesColorStatus;
    private String abnormalSymptoms;
    private List<String> symptoms; // 특이 증상
    private String healthStatus;
    private Boolean hospitalVisit; // 병원 내진 여부
    private String diagnosisName;  // 진단명
    private String prescription;   // 검사 및 처방 내역
}
