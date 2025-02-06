package LittlePet.UMC.User.service;

import LittlePet.UMC.HealthRecord.repository.HealthRecordRepository;
import LittlePet.UMC.User.converter.PetProfileConverter;
import LittlePet.UMC.User.dto.PetProfileRequest.PetProfileRequestDTO;
import LittlePet.UMC.User.dto.PetProfileResponse.PetProfileAllResponseDTO;
import LittlePet.UMC.User.dto.PetProfileResponse.PetProfileResponseDTO;
import LittlePet.UMC.User.repository.PetCategoryProfileRepository;
import LittlePet.UMC.User.repository.UserPetRepository;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetProfileService {

    private final UserRepository userRepository;
    private final PetCategoryProfileRepository petCategoryRepository;
    private final UserPetRepository userPetRepository;
    private final HealthRecordRepository healthRecordRepository;

    @Transactional
    public PetProfileResponseDTO addPetProfile(Long userId, PetProfileRequestDTO petRequestDTO) {
        // 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // PetCategory 검증
        PetCategory category = petCategoryRepository.findBySpecies(petRequestDTO.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 반려동물 카테고리입니다."));

        // 반려동물 엔티티 생성
        UserPet newPet = PetProfileConverter.toUserPetEntity(petRequestDTO, user, category);
        userPetRepository.save(newPet);

        // DTO 변환 및 반환
        return PetProfileConverter.toPetResponseDTO(newPet);
    }

    /**
     * 반려동물 프로필 수정
     */
    @Transactional
    public PetProfileResponseDTO updatePetProfile(Long userId, Long petId, PetProfileRequestDTO petRequestDTO) {
        // 기존 반려동물 정보 조회
        UserPet pet = userPetRepository.findByIdAndUserId(petId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물을 찾을 수 없습니다."));

        // PetCategory 검증
        PetCategory category = petCategoryRepository.findBySpecies(petRequestDTO.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 반려동물 카테고리입니다."));

        // 반려동물 정보 업데이트
        pet.updatePetInfo(
                petRequestDTO.getName(),
                LocalDate.parse(petRequestDTO.getBirthDay()),
                Gender.valueOf(petRequestDTO.getGender().toUpperCase())
        );

        userPetRepository.save(pet);

        // DTO 변환 및 반환
        return PetProfileConverter.toPetResponseDTO(pet);
    }

    /**
     * 반려동물 단일 조회
     */
    public PetProfileResponseDTO getPetProfile(Long userId, Long petId) {
        UserPet pet = userPetRepository.findByIdAndUserId(petId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물을 찾을 수 없습니다."));
        return PetProfileConverter.toPetResponseDTO(pet);
    }

    /**
     * 반려동물 삭제
     */
    @Transactional
    public void deletePetProfile(Long userId, Long petId) {
        UserPet pet = userPetRepository.findByIdAndUserId(petId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물을 찾을 수 없습니다."));

        healthRecordRepository.deleteByUserPet(pet);

        userPetRepository.delete(pet);
    }



    public List<PetProfileAllResponseDTO> getPetsByUserId(Long userId){
        List<UserPet> userPets = userPetRepository.findByUserId(userId);

        return userPets.stream()
                .map(PetProfileConverter::toPetHealthRecordResponseDTO)
                .collect(Collectors.toList());
    }

}
