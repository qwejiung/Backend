package LittlePet.UMC.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequestDTO {

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @Pattern(regexp = "^(010-\\d{4}-\\d{4})$", message = "전화번호 형식이 유효하지 않습니다.")
    private String phone;

    @Size(max = 50, message = "자기소개는 최대 50자까지 가능합니다.")
    private String introduction;
}
