package LittlePet.UMC.repository;

import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.enums.PostType;
import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.Review;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.userEntity.User;

import java.time.LocalDate;

public class CreateEntity {
    // 테스트 데이터 설정 메소드
    public static PostCategory createPostCategory() {
        return PostCategory.builder()
                .posttype(PostType.Q_A)
                .build();
    }

    public static User createUser() {
        return User.builder()
                .name("John Doe")
                .phone("010-1234-5678")
                .gender(Gender.MALE)
                .email("john.doe@example.com")
                .socialId("john_doe_123")
                .socialProvider(SocialProviderEnum.KAKAO)
                .role(RoleStatus.USER)
                .introduction("Hello! I'm John.")
                .profilePhoto("profile.jpg")
                .build();
    }

    public static PetCategory createPetCategory(PetBigCategory petBigCategory) {
        return PetCategory.builder()
                .species("토끼")
                .features("특징")
                .foodInfo("토끼의 정보")
                .environment("키우는 환경")
                .playMethods("놀이방법")
                .mustHaveItemFood("필수템 - 사료")
                .mustHaveItemToys("필수템 - 장난감")
                .mustHaveItemCage("필수템 - 케이지")
                .featureImagePath("rabbit.jpg")
                .petBigCategory(petBigCategory)
                .build();
    }

    public static PetBigCategory createPetBigCategory() {
        return PetBigCategory.builder()
                .categoryName("설치류")
                .build();
    }

    public static Post creatPost(PostCategory postCategory, User user, PetCategory petCategory) {
        return Post.builder()
                .title("안녕하세요")
                .content("저는 이번에 처음 토끼를 키우게 된 ...")
                .views(0L)
                .postCategory(postCategory)
                .petSmallCategory(petCategory)
                .user(user)
                .build();
    }

    public static UserPet createUserPet(PetCategory petCategory, User user) {
        return UserPet.builder()
                .name("오늘이")
                .birthDay(LocalDate.of(2024, 1, 12))
                .gender(Gender.MALE)
                .profilePhoto("profile_photo.jpg")
                .petCategory(petCategory)
                .user(user)
                .build();
    }

    public static Hospital createHospital() {
        return Hospital.builder()
                .placeId(1L)
                .name("튼튼한 병원")
                .address("강서로 47길 112 마곡 엠펠리스파크 2층")
                .build();
    }

    public static Review createReview(PetCategory petCategory,User user,Hospital hospital) {
        return Review.builder()
                .content("간호사 선생님이 친절해요!")
                .rating(5)
                .petGender(Gender.FEMALE)
                .petCategory(petCategory)
                .user(user)
                .hospital(hospital)
                .build();
    }
}
