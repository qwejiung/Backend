package LittlePet.UMC.repository.postRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.CreateEntity;
import LittlePet.UMC.repository.UserRepository;
import LittlePet.UMC.repository.petRepository.categories.PetBigCategoryRepository;
import LittlePet.UMC.repository.petRepository.categories.PetCategoryRepository;
import LittlePet.UMC.repository.postRepository.PostCategoryRepository;
import LittlePet.UMC.repository.postRepository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class PostLikeRepositoryTest {
    @Autowired private PostLikeRepository postLikeRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PostCategoryRepository postCategoryRepository;
    @Autowired private PetCategoryRepository petCategoryRepository;
    @Autowired private PetBigCategoryRepository petBigCategoryRepository;
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("PostLike Entity Create Test")
    public void PostLikeEntityCreateTest() {
        PostCategory postCategory = CreateEntity.createPostCategory();
        User user = CreateEntity.createUser();
        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
        PetCategory petCategory = CreateEntity.createPetCategory(petBigCategory);
        UserPet userpet = CreateEntity.createUserPet(petCategory,user);
        Post post = CreateEntity.creatPost(postCategory,user,userpet);

        // 엔티티 저장
        postCategoryRepository.save(postCategory);
        userRepository.save(user);
        petBigCategoryRepository.save(petBigCategory);
        petCategoryRepository.save(petCategory);
        postRepository.save(post);

        //PostLike 엔티티 생성 및 저장
        PostLike postlike = PostLike.builder()
                .post(post)
                .user(user)
                .build();
        PostLike savedPostLike = postLikeRepository.save(postlike);
        System.out.println(savedPostLike);
    }

}