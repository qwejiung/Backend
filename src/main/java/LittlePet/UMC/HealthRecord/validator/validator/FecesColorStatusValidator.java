package LittlePet.UMC.HealthRecord.validator.validator;

import LittlePet.UMC.HealthRecord.validator.annotation.ValidFecesColorStatus;
import LittlePet.UMC.domain.enums.FecesStatusEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class FecesColorStatusValidator implements ConstraintValidator<ValidFecesColorStatus,String> {

    private String fecesStatus;

    public void initialize(ValidFecesColorStatus constraint) {
    }

    @Override
    public boolean isValid(String fecesColorStatus, ConstraintValidatorContext context) {
        FecesStatusEnum fecesStatusEnum;
        try {
            fecesStatusEnum = FecesStatusEnum.fromDescription(fecesStatus);
        } catch (IllegalArgumentException e) {
            return false; // 유효하지 않은 값이면 false
        }

        // ✅ "대변 안 봄"이면 null 허용
        if (fecesStatusEnum == FecesStatusEnum.NOT_DEFECATED) {
            return true;
        }

        // ❌ "대변 안 봄"이 아니라면, fecesColorStatus 필수 입력
        return fecesColorStatus != null && !fecesColorStatus.trim().isEmpty();
    }
}
