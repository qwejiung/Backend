package LittlePet.UMC.repository.postRepository.mapping;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.community.repository.CommentRepository;
import LittlePet.UMC.domain.enums.Gender;
import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
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
public class CommentRepositoryTest {

    @Autowired private CommentRepository commentRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PostCategoryRepository postCategoryRepository;
    @Autowired private PetCategoryRepository petCategoryRepository;
    @Autowired private PetBigCategoryRepository petBigCategoryRepository;
    @Autowired private UserRepository userRepository;


    @Test
    @DisplayName("Comment Entity Create Test")
    public void CommentRepositoryTest() {
        PostCategory postCategory = CreateEntity.createPostCategory();
        User user = CreateEntity.createUser();
        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
        Post post = CreateEntity.creatPost(postCategory,user,petBigCategory);

        // 엔티티 저장
        postCategoryRepository.save(postCategory);
        userRepository.save(user);
        petBigCategoryRepository.save(petBigCategory);
        postRepository.save(post);

        //Comment 엔티티 생성 및 저장
        Comment comment = Comment.builder()
                .content("토끼가 너무 귀여워요!")
                .post(post)
                .user(user)
                .parent(null)
                .build();
        Comment savedComment=commentRepository.save(comment);
        System.out.println(savedComment);



    }

}