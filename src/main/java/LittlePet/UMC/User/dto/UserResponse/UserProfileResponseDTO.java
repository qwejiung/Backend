package LittlePet.UMC.User.dto.UserResponse;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {
    private String name;
    private String role;
    private String introduction;
    private String profilePhoto;
    private int postCount;
    private int commentCount;
    private int likeCount;
    private int reviewCount;
    private int scrapCount;
    private List<PetProfileDTO> petProfile;
    private List<PetInfoDTO> userPet;
    private List<BadgeInfoDTO> userBadge;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PetProfileDTO {
        private String petSpices;
        private String imageUrl;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PetInfoDTO {
        private Long petId;
        private String name;
        private String profilePhoto;
        private String petCategory;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BadgeInfoDTO {
        private String name;
    }
}
