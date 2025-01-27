package LittlePet.UMC.Badge.validation.validator;

import LittlePet.UMC.Badge.repository.badgeRepository.BadgeRepository;
import LittlePet.UMC.Badge.validation.annotation.ExistBadgeType;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BadgeTypeExistValidator implements ConstraintValidator<ExistBadgeType,String> {


    private final BadgeRepository badgeRepository;
    @Override
    public void initialize(ExistBadgeType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        Boolean isValid = badgeRepository.existByBadgeType(s);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.BADGE_NOT_FOUND.toString()).addConstraintViolation();

        }
        return isValid;
    }
}
