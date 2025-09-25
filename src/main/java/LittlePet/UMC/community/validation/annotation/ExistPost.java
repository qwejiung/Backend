package LittlePet.UMC.community.validation.annotation;

import LittlePet.UMC.Badge.validation.validator.BadgeTypeExistValidator;
import LittlePet.UMC.community.validation.validator.PostExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PostExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistPost {
    String message() default "존재하지 않는 게시물입니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
