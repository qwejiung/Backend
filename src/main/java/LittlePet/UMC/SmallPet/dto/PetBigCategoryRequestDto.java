package LittlePet.UMC.SmallPet.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;


public class PetBigCategoryRequestDto {
    @Getter
    public static class WriteDTO {
        @NotBlank(message = "카테고리 이름은 필수로 입력해주세요.")
        String categoryName;
    }
}
