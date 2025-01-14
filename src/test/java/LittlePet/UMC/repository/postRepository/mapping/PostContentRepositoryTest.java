package LittlePet.UMC.repository.postRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.enums.MediaTypeEnum;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.postEntity.mapping.PostContent;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.repository.CreateEntity;
import LittlePet.UMC.repository.UserRepository;
import LittlePet.UMC.repository.petRepository.categories.PetBigCategoryRepository;
import LittlePet.UMC.repository.petRepository.categories.PetCategoryRepository;
import LittlePet.UMC.repository.petRepository.mapping.UserPetRepository;
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
public class PostContentRepositoryTest {

    //리포지토리들

    @Autowired private PostCategoryRepository postCategoryRepository;
    @Autowired private PetBigCategoryRepository petBigCategoryRepository;
    @Autowired private PetCategoryRepository petCategoryRepository;
    @Autowired private UserPetRepository userPetRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostContentRepository postContentRepository;

    @Test
    @DisplayName("PostContent Entity Create Test")
    public void PostContentRepositoryTest() {
        //외래키 엔티티 생성
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
        userPetRepository.save(userpet);
        postRepository.save(post);

        //PostContent 엔티티 생성
        PostContent postContent = PostContent.builder()
                .mediaType(MediaTypeEnum.Text)
                .content("토끼가 밥을 안 먹어요 어쩌구..")
                .filePath(null)
                .sequence(1)
                .post(post)
                .build();

        PostContent savedPostContent = postContentRepository.save(postContent);
        System.out.println("savedPostContent: " + savedPostContent);
    }

    @Test
    @DisplayName("PostContent-이미지")
    public void PostContentImageRepositoryTest() {
        //외래키 엔티티 생성
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
        userPetRepository.save(userpet);
        postRepository.save(post);

        //PostContent 엔티티 생성
        PostContent postContent = PostContent.builder()
                .mediaType(MediaTypeEnum.Text)
                .content(null)
                .filePath("DSfsdfsdfsdfsdfsdgsdgsdgsdfsdfafsdagg")
                .sequence(2)
                .post(post)
                .build();

        PostContent savedPostContent = postContentRepository.save(postContent);
        System.out.println("savedPostContent: " + savedPostContent);
    }


}