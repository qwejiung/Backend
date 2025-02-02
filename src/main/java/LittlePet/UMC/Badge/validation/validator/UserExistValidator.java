package LittlePet.UMC.Badge.validation.validator;

import LittlePet.UMC.Badge.validation.annotation.ExistUser;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserExistValidator implements ConstraintValidator<ExistUser, Long> {

    private final UserRepository userRepository;


    @Override
    public void initialize(ExistUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long UserId, ConstraintValidatorContext context) {
        Boolean isValid = userRepository.existsById(UserId);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.USER_NOT_FOUND.toString()).addConstraintViolation();

        }
        return isValid;
    }
}
