package LittlePet.UMC.User.dto.UserResponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateProfileResponseDTO {

    private Long userId;
    private String nickname;
    private String phone;
    private String introduction;
}
