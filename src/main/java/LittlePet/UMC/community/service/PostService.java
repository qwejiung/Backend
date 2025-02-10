package LittlePet.UMC.community.service;

import LittlePet.UMC.SmallPet.repository.PetCategoryRepository;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.apiPayload.exception.handler.PetCategoryHandler;
import LittlePet.UMC.apiPayload.exception.handler.PostCategoryHandler;
import LittlePet.UMC.apiPayload.exception.handler.PostHandler;
import LittlePet.UMC.apiPayload.exception.handler.UserHandler;
import LittlePet.UMC.community.dto.PostForm;
import LittlePet.UMC.community.repository.PostCategoryRepository;
import LittlePet.UMC.community.repository.postRepository.PostRepository;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.postEntity.mapping.PostContent;
import LittlePet.UMC.domain.userEntity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Post createPost(PostForm postForm, Long userId, List<String> urls) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        PetCategory petCategory = petCategoryRepository.findFirstBySpecies(postForm.getSmallPetCategory())
                .orElseThrow(() ->  new PetCategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));

        PostCategory postCategory = postCategoryRepository.findFirstByCategory(postForm.getPostCategory())
                .orElseThrow(() -> new PostCategoryHandler(ErrorStatus.POST_CATEGORY_NOT_FOUND));
        //url은 종류가 image로 들어가면서 저장하면될듯?

        Post post = Post.createPost(postForm.getTitle(),0l,user,postCategory,petCategory);

        List<PostContent> contents = postForm.getContents().stream()
                .map(contentForm -> PostContent.createPostContentText(
                        contentForm.getContent(),
                        post))
                .collect(Collectors.toList());

        for (String url : urls) {
            contents.add(PostContent.createPostContentPicture(url, post));
        }

        post.addPostContent(contents);

        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post updatePost(Long postId, PostForm postForm,List<String> urls) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));

        if (postForm.getTitle() != null && !postForm.getTitle().isEmpty() && !postForm.getTitle().equals("string")) {
            post.setTitle(postForm.getTitle());
        }

        if (postForm.getPostCategory() != null && !postForm.getPostCategory().equals("string")) {
            PostCategory postCategory = postCategoryRepository.findFirstByCategory(postForm.getPostCategory())
                    .orElseThrow(() -> new PostCategoryHandler(ErrorStatus.POST_CATEGORY_NOT_FOUND));
            post.setPostCategory(postCategory);

        }

        if (postForm.getSmallPetCategory() != null && !postForm.getSmallPetCategory().equals("string")) {
            PetCategory petCategory = petCategoryRepository.findFirstBySpecies(postForm.getSmallPetCategory())
                    .orElseThrow(() -> new PetCategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
            post.setPetCategory(petCategory);
        }

        if (postForm.getContents() != null && !postForm.getContents().isEmpty() && !postForm.getContents().equals("string")) {
            post.resetSequenceCounter();
            List<PostContent> contents = postForm.getContents().stream()
                    .map(contentForm -> PostContent.createPostContentText(
                            contentForm.getContent(),
                            post))
                    .collect(Collectors.toList());

            for (String url : urls) {
                contents.add(PostContent.createPostContentPicture(url, post));
            }

            post.getPostcontentList().clear();
            post.addPostContent(contents);
        }

        return post;
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
        postRepository.delete(post);
    }


    public Post FindOnePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
        return post;

    }

    public List<Post> FindAllPost(String category, int pageNum, int size, String sort) {
        Pageable pageable = PageRequest.of(pageNum, size);

        // 최신순 기본값
        if (sort.equalsIgnoreCase("최신순")) {
            return postRepository.findLatestPostsByCategory(category, pageable).getContent();
        } else if (sort.equalsIgnoreCase("인기순")) {
            return postRepository.findPopularPostsByCategory(category, pageable).getContent();
        }

        // 만약 이상한 정렬 기준이 들어오면 기본 최신순 적용
        return postRepository.findLatestPostsByCategory(category, pageable).getContent();
    }
}