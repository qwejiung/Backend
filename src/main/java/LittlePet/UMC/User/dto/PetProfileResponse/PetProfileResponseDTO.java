package LittlePet.UMC.User.dto.PetProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetProfileResponseDTO {
    private Long petId;
    private String name;
    private String birthDay;
    private String gender;
    private String profilePhoto;
    private String categoryName; // PetCategory 이름
}
