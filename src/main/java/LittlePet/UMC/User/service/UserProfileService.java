package LittlePet.UMC.User.service;

import LittlePet.UMC.User.converter.UserProfileConverter;
import LittlePet.UMC.User.dto.UserRequest.UserProfileRequestDTO;
import LittlePet.UMC.User.dto.UserResponse.UserProfileResponseDTO;
import LittlePet.UMC.User.dto.UserResponse.UserUpdateProfileResponseDTO;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.apiPayload.exception.handler.UserHandler;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    public UserProfileResponseDTO getUserProfile(Long userId) {
        // 유저 조회, 없으면 CustomException 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 1. 컨버터를 사용해 DTO 변환
        UserProfileResponseDTO response = UserProfileConverter.toUserResponseDto(user);

        // 2. petProfile에서 같은 petSpices(종) 값이 중복되면 하나만 남기도록 필터링
        List<UserProfileResponseDTO.PetProfileDTO> distinctPetProfiles = response.getPetProfile().stream()
                .collect(Collectors.toMap(
                        UserProfileResponseDTO.PetProfileDTO::getPetSpices, // Key: petSpices (종)
                        pet -> pet, // Value: 첫 번째 petProfile 데이터
                        (existing, replacement) -> existing, // 중복되면 기존 값 유지
                        LinkedHashMap::new // 순서 유지
                ))
                .values()
                .stream()
                .collect(Collectors.toList());

        // 3. 필터링된 데이터를 적용
        return UserProfileResponseDTO.builder()
                .name(response.getName())
                .role(response.getRole())
                .introduction(response.getIntroduction())
                .profilePhoto(response.getProfilePhoto())
                .postCount(response.getPostCount())
                .commentCount(response.getCommentCount())
                .likeCount(response.getLikeCount())
                .reviewCount(response.getReviewCount())
                .scrapCount(response.getScrapCount())
                .petProfile(distinctPetProfiles) // 중복 없는 대표 펫 리스트
                .userPet(response.getUserPet())
                .userBadge(response.getUserBadge())
                .build();


    }

    @Transactional
    public UserUpdateProfileResponseDTO updateProfile(Long userId, UserProfileRequestDTO request,String imageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 중복 닉네임 검증
        // 🚨 중복 닉네임 검증 (자기 닉네임이면 검증 스킵)
        if (!user.getName().equals(request.getNickname()) && userRepository.existsByNameAndIdNot(request.getNickname(), userId)) {
            throw new UserHandler(ErrorStatus.DUPLICATE_NICKNAME);
        }

        String updatedProfilePhoto = (imageUrl != null) ? imageUrl : user.getProfilePhoto();

        // 사용자 정보 업데이트
        // toBuilder()를 사용해 기존 엔티티의 값을 유지하면서 업데이트할 필드만 변경
        User updatedUser = user.toBuilder()
                .name(request.getNickname())
                .phone(request.getPhone())
                .introduction(request.getIntroduction())
                .profilePhoto(updatedProfilePhoto)
                .build();

        userRepository.save(updatedUser);



        return UserProfileConverter.toUpdateResponse(updatedUser);
    }

    @Transactional
    public UserUpdateProfileResponseDTO getUserIdProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return UserProfileConverter.toUpdateResponse(user);
    }
}
