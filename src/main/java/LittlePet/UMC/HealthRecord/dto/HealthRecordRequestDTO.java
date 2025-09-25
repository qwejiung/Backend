package LittlePet.UMC.HealthRecord.dto;

import LittlePet.UMC.HealthRecord.validator.annotation.ExistHospital;
import LittlePet.UMC.HealthRecord.validator.annotation.ValidFecesColorStatus;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "몸무게는 필수 입력값입니다.")
    private Double weight;

    @NotBlank(message = "식사량은 필수 입력값입니다.")
    private String mealAmount;

    @NotBlank(message = "배변 상태는 필수 입력값입니다.")
    private String fecesStatus;

    //@NotBlank(message = "배변 색상은 상태는 필수 입력값입니다.")
    private String fecesColorStatus;

    private String atypicalSymptom; // 특이 증상 (기타일 경우 입력)

    @Size(min = 1, max = 50, message = "특이 증상(기타)는 최소 1자 이상, 최대 50자 이하여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]{1,50}$", message = "특이 증상(기타)는 특수문자를 포함할 수 없습니다.") // 🚨 특수문자 제외
    private String otherSymptom;

    @NotBlank(message = "건강 상태는 필수 입력값입니다.")
    private String healthStatus;

    @NotNull(message = "병원 내진 여부는 필수 입력값입니다.")
    private Boolean hospitalVisit;

    @Size(min = 1, max = 20, message = "진단명은 최소 1자 이상, 최대 20자 이하여야 합니다.")
    private String diagnosisName; // 🚨 특수문자 가능 → 별도 패턴 제한 없음

    @Size(min = 1, max = 300, message = "처방 내용은 최소 1자 이상, 최대 300자 이하여야 합니다.")
    private String prescription; // 🚨 특수문자 가능 → 별도 패턴 제한 없음
}
