package LittlePet.UMC.repository.postRepository;

import LittlePet.UMC.UmcApplication;
import LittlePet.UMC.domain.enums.PostType;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.userEntity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = UmcApplication.class)
public class PostCategoryRepositoryTest {

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Test
    @DisplayName("PostCategory Entity Create Test")
    public void PostCategoryCreateTest() {

        //PostCategory 엔티티 생성 및 저장
        PostCategory postcategory = PostCategory.builder()
                .posttype(PostType.Q_A)
                .build();
        PostCategory newpostcategory= postCategoryRepository.save(postcategory);
        System.out.println(newpostcategory);
    }

}