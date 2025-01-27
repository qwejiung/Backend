package LittlePet.UMC.User.converter;

import LittlePet.UMC.User.dto.PetProfileRequest.PetProfileRequestDTO;
import LittlePet.UMC.User.dto.PetProfileResponse.PetProfileResponseDTO;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.userEntity.User;

import java.time.LocalDate;

public class PetProfileConverter {
    public static PetProfileResponseDTO toPetResponseDTO(UserPet userPet) {
        return PetProfileResponseDTO.builder()
                .petId(userPet.getId())
                .name(userPet.getName())
                .birthDay(userPet.getBirthDay().toString())
                .gender(userPet.getGender().toString())
                .profilePhoto(userPet.getProfilePhoto())
                .categoryName(userPet.getPetCategory().getSpecies()) // PetCategory 이름
                .build();
    }

    // RequestDTO -> Entity 변환
    public static UserPet toUserPetEntity(PetProfileRequestDTO petRequestDTO, User user, PetCategory category) {
        return UserPet.builder()
                .name(petRequestDTO.getName())
                .birthDay(LocalDate.parse(petRequestDTO.getBirthDay())) // String -> LocalDate 변환
                .gender(Gender.valueOf(petRequestDTO.getGender().toUpperCase())) // Enum 변환
                .profilePhoto(petRequestDTO.getProfilePhoto())
                .user(user)
                .petCategory(category)
                .build();
    }
}
