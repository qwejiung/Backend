package LittlePet.UMC.User.dto.Login;

import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {



    @Getter
    @Setter
    @Builder
    public static class UserJoinDTO{
        private RoleStatus role;
        private String username;
        private String userId;
        private String phone;
        private String name;
        private String socialId;
        private String email;
        private SocialProviderEnum socialProvider;
    }
}
