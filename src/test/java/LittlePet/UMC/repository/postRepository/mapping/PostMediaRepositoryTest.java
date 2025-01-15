//package LittlePet.UMC.repository.postRepository.mapping;
//
//import LittlePet.UMC.UmcApplication;
//import LittlePet.UMC.domain.enums.MediaTypeEnum;
//import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
//import LittlePet.UMC.domain.petEntity.categories.PetCategory;
//import LittlePet.UMC.domain.postEntity.Post;
//import LittlePet.UMC.domain.postEntity.PostCategory;
//import LittlePet.UMC.domain.userEntity.User;
//import LittlePet.UMC.repository.CreateEntity;
//import LittlePet.UMC.repository.UserRepository;
//import LittlePet.UMC.repository.petRepository.categories.PetBigCategoryRepository;
//import LittlePet.UMC.repository.petRepository.categories.PetCategoryRepository;
//import LittlePet.UMC.repository.postRepository.PostCategoryRepository;
//import LittlePet.UMC.repository.postRepository.PostRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//@TestPropertySource(locations = "classpath:application-test.properties")
//@SpringBootTest(classes = UmcApplication.class)
//public class PostMediaRepositoryTest {
//
//
//    @Autowired private PostRepository postRepository;
//    @Autowired private PostCategoryRepository postCategoryRepository;
//    @Autowired private PetCategoryRepository petCategoryRepository;
//    @Autowired private PetBigCategoryRepository petBigCategoryRepository;
//    @Autowired private UserRepository userRepository;
//    @Autowired private PostMediaRepository postMediaRepository;
//
//    @Test
//    @DisplayName("PostMedia Entity Create Test")
//    public void PostMediaEntityCreateTest() {
//        //외래키 엔티티 생성
//        PostCategory postCategory = CreateEntity.createPostCategory();
//        User user = CreateEntity.createUser();
//        PetBigCategory petBigCategory = CreateEntity.createPetBigCategory();
//        PetCategory petCategory = CreateEntity.createPetCategory(petBigCategory);
//        Post post = CreateEntity.creatPost(postCategory, user, petCategory);
//
//        // 엔티티 저장
//        postCategoryRepository.save(postCategory);
//        userRepository.save(user);
//        petBigCategoryRepository.save(petBigCategory);
//        petCategoryRepository.save(petCategory);
//        postRepository.save(post);
//
//
//        //PostMedia 엔티티 생성 및 저장
//        PostMedia postmedia = PostMedia.builder()
//                .mediaType(MediaTypeEnum.Picture)
//                .post(post)
//                .filePath("C:\\Users\\DESKTOP\\Desktop\\mypicture.jpg")
//                .build();
//
//        PostMedia savedmedia = postMediaRepository.save(postmedia);
//        System.out.println(savedmedia);
//    }
//}