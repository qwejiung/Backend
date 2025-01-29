package LittlePet.UMC.HealthRecord.validator.validator;

import LittlePet.UMC.HealthRecord.dto.HealthRecordRequestDTO;
import LittlePet.UMC.HealthRecord.validator.annotation.ExistHospital;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.apiPayload.exception.handler.HospitalHandler;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;




@Component
public class CheckHospitalVisitValidator implements ConstraintValidator<ExistHospital, HealthRecordRequestDTO> {

    @Override
    public boolean isValid(HealthRecordRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return false;
        }

        System.out.println("[Validator] 병원 내진 여부: " + dto.getHospitalVisit());

        // ❌ 기존 방식: Hibernate Validator 내부에서 예외 발생 → Spring이 감지 못함
        // throw new HospitalHandler(ErrorStatus.HOSPITAL_VISIT_ERROR);

        // ✅ 해결 방법: Hibernate Validator 방식에 맞춰 ConstraintViolation을 설정
        context.disableDefaultConstraintViolation(); // 기본 메시지 비활성화

        if (Boolean.TRUE.equals(dto.getHospitalVisit())) {
            if (dto.getDiagnosisName() == null || dto.getDiagnosisName().isBlank()) {
                System.out.println("[Validator] 병원 내진 여부가 true인데 diagnosisName이 없음!");
                context.buildConstraintViolationWithTemplate(ErrorStatus.HOSPITAL_VISIT_ERROR.getMessage())
                        .addConstraintViolation();
                return false;
            }
            if (dto.getPrescription() == null || dto.getPrescription().isBlank()) {
                System.out.println("[Validator] 병원 내진 여부가 true인데 prescription이 없음!");
                context.buildConstraintViolationWithTemplate(ErrorStatus.HOSPITAL_VISIT_ERROR.getMessage())
                        .addConstraintViolation();
                return false;
            }
        }

        if (Boolean.FALSE.equals(dto.getHospitalVisit())) {
            if (dto.getDiagnosisName() != null || dto.getPrescription() != null) {
                System.out.println("[Validator] 병원 내진 여부가 false인데 diagnosisName과 prescription이 존재!");
                context.buildConstraintViolationWithTemplate(ErrorStatus.HOSPITAL_VISIT_NULL_ERROR.getMessage())
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}