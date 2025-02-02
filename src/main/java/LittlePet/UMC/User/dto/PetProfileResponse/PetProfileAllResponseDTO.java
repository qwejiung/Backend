package LittlePet.UMC.User.dto.PetProfileResponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetProfileAllResponseDTO {
    private Long petId;
    private String name;
    private String profilePhoto;
}
