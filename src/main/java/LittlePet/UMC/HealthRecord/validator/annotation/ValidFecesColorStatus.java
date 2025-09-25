package LittlePet.UMC.HealthRecord.validator.annotation;

import LittlePet.UMC.HealthRecord.validator.validator.FecesColorStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FecesColorStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFecesColorStatus {

    String message() default "배변 상태가 '대변 안 봄'이 아닐 경우 배변 색상은 필수 입력값입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
