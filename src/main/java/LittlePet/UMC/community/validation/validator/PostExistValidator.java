package LittlePet.UMC.community.validation.validator;

import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.community.repository.postRepository.PostRepository;
import LittlePet.UMC.community.repository.postlikerepository.PostLikeRepository;
import LittlePet.UMC.community.validation.annotation.ExistPost;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostExistValidator implements ConstraintValidator<ExistPost,Long> {
    private final PostRepository postRepository;

    @Override
    public void initialize(ExistPost constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long PostId, ConstraintValidatorContext context) {
        Boolean isValid = postRepository.existsById(PostId);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.POST_NOT_FOUND.toString()).addConstraintViolation();

        }
        return isValid;
    }
}
