package LittlePet.UMC.Badge.validation.annotation;

import LittlePet.UMC.Badge.validation.validator.UserExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistUser {
    String message() default "유저 아이디를 다시 한번 확인해주세요";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
