package LittlePet.UMC.HealthRecord.dto;

import LittlePet.UMC.HealthRecord.validator.annotation.ExistHospital;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExistHospital
public class HealthRecordRequestDTO {

    private String recordDate; // 날짜 (YYYY-MM-DD)
    @NotNull(message = "몸무게는 필수 입력값입니다.")
    private Double weight;
    @NotBlank
    private String mealAmount;
    @NotBlank
    private String fecesStatus;
    @NotBlank
    private String fecesColorStatus;
    @NotEmpty
    private List<String> atypicalSymptom; // 특이 증상
    private String otherSymptom; // 사용자가 입력한 "기타" 증상 추가
    @NotBlank
    private String healthStatus;
    @NotNull(message = "병원 내진 여부는 필수 입력값입니다.") // 🚨 추가됨
    private Boolean hospitalVisit; // 병원 내진 여부
    private String diagnosisName;  // 진단명
    private String prescription;   // 검사 및 처방 내역
}
