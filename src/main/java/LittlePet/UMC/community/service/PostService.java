package LittlePet.UMC.community.service;

import LittlePet.UMC.SmallPet.repository.PetCategoryRepository;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.community.dto.PostForm;
import LittlePet.UMC.community.repository.PostCategoryRepository;
import LittlePet.UMC.community.repository.PostRepository;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.postEntity.mapping.PostContent;
import LittlePet.UMC.domain.userEntity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PetCategoryRepository petCategoryRepository;

    @Transactional //위에서 readOnly해서 따로 해줘야 저장됨 : 디폴트가 false
    public Post createPost(PostForm postForm, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PetCategory petCategory = petCategoryRepository.findBySpecies(postForm.getSmallPetCategory())
                .orElseThrow(() -> new IllegalArgumentException("해당 동물이 존재하지 않습니다."));

        PostCategory postCategory = postCategoryRepository.findByCategory(postForm.getPostCategory())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));


        Post post = Post.createPost(postForm.getTitle(),0l,user,postCategory,petCategory);

        List<PostContent> contents = postForm.getContents().stream()
                .map(contentForm -> PostContent.createPostContent(
                        contentForm.getMediaType(),
                        contentForm.getContent(),
                        contentForm.getSequence(),
                        post))
                .collect(Collectors.toList());

        post.addPostContent(contents);

        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post updatePost(Long postId, PostForm postForm) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (postForm.getTitle() != null && !postForm.getTitle().isEmpty()) {
            post.setTitle(postForm.getTitle());
        }

        if (postForm.getPostCategory() != null) {
            PostCategory postCategory = postCategoryRepository.findByCategory(postForm.getPostCategory())
                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
            post.setPostCategory(postCategory);

        }

        if (postForm.getSmallPetCategory() != null) {
            PetCategory petCategory = petCategoryRepository.findBySpecies(postForm.getSmallPetCategory())
                    .orElseThrow(() -> new IllegalArgumentException("해당 동물이 존재하지 않습니다."));
            post.setPetBigCategory(petCategory.getPetBigCategory());
        }

        if (postForm.getContents() != null && !postForm.getContents().isEmpty()) {
            List<PostContent> contents = postForm.getContents().stream()
                    .map(contentForm -> PostContent.createPostContent(
                            contentForm.getMediaType(),
                            contentForm.getContent(),
                            contentForm.getSequence(),
                            post))
                    .collect(Collectors.toList());

            post.getPostcontentList().clear();
            post.addPostContent(contents);
        }

        return post;
    }


    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postRepository.delete(post);
    }
}