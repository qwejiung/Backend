package LittlePet.UMC.User.service;

import LittlePet.UMC.User.dto.Login.*;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import LittlePet.UMC.domain.userEntity.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("[DEBUG] OAuth2UserRequest: " + userRequest);
        System.out.println("[DEBUG] ClientRegistration ID: " + userRequest.getClientRegistration().getRegistrationId());
        System.out.println("[DEBUG] Access Token: " + userRequest.getAccessToken().getTokenValue());
        OAuth2User oAuth2User;

        try {
            oAuth2User = super.loadUser(userRequest);
            System.out.println("[DEBUG] OAuth2User attributes: " + oAuth2User.getAttributes());
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load user: " + e.getMessage());
            e.printStackTrace();
            throw new NullPointerException();
        }

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("[DEBUG] Registration ID: " + registrationId);

        OAuth2Response oAuth2Response;

        try {
            if ("google".equals(registrationId)) {
                oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
                System.out.println("[DEBUG] GoogleResponse: " + oAuth2Response);
            } else if ("naver".equals(registrationId)) {
                oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
                System.out.println("[DEBUG] NaverResponse: " + oAuth2Response);
            } else {
                throw new OAuth2AuthenticationException("Unsupported registration provider: " + registrationId);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to parse response: " + e.getMessage());
            e.printStackTrace();
            throw new NullPointerException();
        }



        String provider = oAuth2Response.getProvider();
        System.out.println("Provider: " + provider);

        SocialProviderEnum socialProvider;
        try {
            socialProvider = SocialProviderEnum.fromString(provider);
        } catch (IllegalArgumentException e) {
            throw new OAuth2AuthenticationException("Unknown provider: " + provider);
        }

        String socialId = oAuth2Response.getProviderId();
        System.out.println("[DEBUG] Checking existing user with socialId: " + socialId);
        User existingUser = userRepository.findBySocialId(socialId);
        System.out.println("[DEBUG] Existing user: " + existingUser);

        if (socialId == null || socialProvider == null) {
            throw new IllegalArgumentException("[ERROR] Missing essential fields: socialId or socialProvider is null");
        }

        if (existingUser == null) {
            // 새로운 사용자 등록
            User newUser = User.builder()
                    .socialId(socialId)
                    .email(oAuth2Response.getEmail())
                    .name(oAuth2Response.getName())
                    .socialProvider(socialProvider)
                    .phone(oAuth2Response.getPhone()) // 최초에는 null로 설정, 이후 업데이트 가능
                    .role(RoleStatus.USER)
                    .build();

            userRepository.save(newUser);

            System.out.println("[DEBUG] Saved new user: " + newUser);

            // DTO 생성
            UserDTO.UserJoinDTO userDTO = UserDTO.UserJoinDTO.builder()
                    .socialId(socialId)
                    .role(RoleStatus.USER)
                    .email(oAuth2Response.getEmail())
                    .name(oAuth2Response.getName())
                    .phone(oAuth2Response.getPhone()) // 초기값
                    .socialProvider(socialProvider)
                    .build();

            return new CustomOAuth2User(userDTO);
        } else {
            // 기존 사용자 정보 업데이트
            User updatedUser = User.builder()
                    .id(existingUser.getId()) // 기존 ID 유지
                    .socialId(existingUser.getSocialId()) // 소셜 ID 유지
                    .email(oAuth2Response.getEmail()) // 업데이트된 이메일
                    .name(oAuth2Response.getName()) // 업데이트된 이름
                    .phone(existingUser.getPhone()) // 기존 번호 유지
                    .socialProvider(existingUser.getSocialProvider())
                    .role(existingUser.getRole()) // 기존 역할 유지
                    .build();

            userRepository.save(updatedUser);

            // DTO 생성
            UserDTO.UserJoinDTO userDTO = UserDTO.UserJoinDTO.builder()
                    .socialId(socialId)
                    .role(existingUser.getRole())
                    .email(oAuth2Response.getEmail())
                    .name(oAuth2Response.getName())
                    .phone(existingUser.getPhone()) // 기존 번호
                    .socialProvider(socialProvider)
                    .build();

            return new CustomOAuth2User(userDTO);
        }
    }
}