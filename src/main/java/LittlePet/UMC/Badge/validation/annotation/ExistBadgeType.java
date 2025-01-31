package LittlePet.UMC.Badge.validation.annotation;

import LittlePet.UMC.Badge.validation.validator.BadgeTypeExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BadgeTypeExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistBadgeType {
    String message() default "'POST_MASTER' , 'COMMENT_GENIUS', 'LIKE_KING' 중에 선택해주세요";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
