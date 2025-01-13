package LittlePet.UMC.repository.postRepository;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.enums.PostType;
import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.UserRepository;
import LittlePet.UMC.repository.petRepository.categories.PetBigCategoryRepository;
import LittlePet.UMC.repository.petRepository.categories.PetCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import LittlePet.UMC.repository.CreateEntity;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class PostRepositoryTest {

    // 리포지토리들
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostCategoryRepository postCategoryRepository;
    @Autowired
    private PetBigCategoryRepository petBigCategoryRepository;
    @Autowired
    private PetCategoryRepository petCategoryRepository;



    @Test
    @DisplayName("Post Entity Create Test")
    public void PostCreateTest() {
        // 데이터 준비
        PostCategory postCategory = CreateEntity.createPostCategory();
        User user = CreateEntity.createUser();
        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
        PetCategory petCategory = CreateEntity.createPetCategory(petBigCategory);

        // 엔티티 저장
        postCategoryRepository.save(postCategory);
        userRepository.save(user);
        petBigCategoryRepository.save(petBigCategory);
        petCategoryRepository.save(petCategory);

        // Post 엔티티 생성 및 저장
        Post post = CreateEntity.creatPost(postCategory,user,petCategory);

        Post savedPost = postRepository.save(post);
        System.out.println(savedPost);
    }
}
