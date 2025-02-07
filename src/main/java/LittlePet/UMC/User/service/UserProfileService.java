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

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    public UserProfileResponseDTO getUserProfile(Long userId) {
        // 유저 조회, 없으면 CustomException 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // Converter를 사용해 DTO 변환
        return UserProfileConverter.toUserResponseDto(user);
    }

    @Transactional
    public UserUpdateProfileResponseDTO updateProfile(Long userId, UserProfileRequestDTO request,String ImageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 중복 닉네임 검증
        if (userRepository.existsByNameAndIdNot(request.getNickname(), userId)) {
            throw new UserHandler(ErrorStatus.DUPLICATE_NICKNAME);
        }

        // 사용자 정보 업데이트
        // toBuilder()를 사용해 기존 엔티티의 값을 유지하면서 업데이트할 필드만 변경
        User updatedUser = user.toBuilder()
                .name(request.getNickname())
                .phone(request.getPhone())
                .introduction(request.getIntroduction())
                .profilePhoto(user.getProfilePhoto())
                .build();

        userRepository.save(updatedUser);



        return UserProfileConverter.toUpdateResponse(updatedUser);
    }
}
