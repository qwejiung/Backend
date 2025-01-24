package LittlePet.UMC.User.service;

import LittlePet.UMC.User.converter.UserProfileConverter;
import LittlePet.UMC.User.dto.UserProfileRequestDTO;
import LittlePet.UMC.User.dto.UserProfileResponseDTO;
import LittlePet.UMC.User.dto.UserUpdateProfileResponseDTO;
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
    public UserUpdateProfileResponseDTO updateProfile(Long userId, UserProfileRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 중복 닉네임 검증
        if (userRepository.existsByNameAndIdNot(request.getNickname(), userId)) {
            throw new UserHandler(ErrorStatus.DUPLICATE_NICKNAME);
        }

        // 사용자 정보 업데이트
        user.updateProfile(request.getNickname(), request.getPhone(), request.getIntroduction());

        return UserProfileConverter.toUpdateResponse(user);
    }
}
