package LittlePet.UMC.HealthRecord.validator.annotation;


import LittlePet.UMC.HealthRecord.validator.validator.CheckHospitalVisitValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CheckHospitalVisitValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE}) // TYPE 추가
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistHospital {

    String message() default "필수 항목을 입력해 주세요";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
