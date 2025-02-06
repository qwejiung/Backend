package LittlePet.UMC.User.dto.PetProfileRequest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetProfileRequestDTO {
    private String name;          // 반려동물 이름
    private String birthDay;      // 반려동물 생년월일
    private String gender;        // 반려동물 성별
    private String categoryName; // PetCategory ID
}
