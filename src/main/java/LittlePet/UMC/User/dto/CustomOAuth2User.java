package LittlePet.UMC.User.dto;

import LittlePet.UMC.domain.enums.SocialProviderEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final UserDTO.UserJoinDTO userJoinDTO;

    public CustomOAuth2User(UserDTO.UserJoinDTO userJoinDTO) {
        this.userJoinDTO = userJoinDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null; // 필요한 경우 OAuth2 속성을 반환하도록 구현
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "USER"); // 기본 권한을 ROLE_USER로 설정
        //authorities.add(() -> userJoinDTO.getRole().name()); // RoleStatus를 문자열로 반환
        return authorities;
    }

    @Override
    public String getName() {
        return userJoinDTO.getName(); // 사용자 이름 반환
    }



    public String getSocialId() {
        return userJoinDTO.getSocialId();
    }



}