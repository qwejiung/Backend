package LittlePet.UMC.community.service;

import LittlePet.UMC.Badge.service.BadgeCommandService;
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
import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;
import LittlePet.UMC.domain.enums.RoleStatus;
import LittlePet.UMC.domain.enums.SocialProviderEnum;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
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
    private final BadgeCommandService badgeCommandService;

    @Transactional //위에서 readOnly해서 따로 해줘야 저장됨 : 디폴트가 false
    public Post createPost(PostForm postForm, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        PetCategory petCategory = petCategoryRepository.findFirstBySpecies(postForm.getSmallPetCategory())
                .orElseThrow(() ->  new PetCategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));

        PostCategory postCategory = postCategoryRepository.findFirstByCategory(postForm.getPostCategory())
                .orElseThrow(() -> new PostCategoryHandler(ErrorStatus.POST_CATEGORY_NOT_FOUND));

        Post post = Post.createPost(postForm.getTitle(),0l,user,postCategory,petCategory);

        List<PostContent> contents = postForm.getContents().stream()
                .map(contentForm -> PostContent.createPostContent(
                        contentForm.getType(),
                        contentForm.getValue(),
                        contentForm.getOrderIndex(),
                        post))
                .collect(Collectors.toList());

        post.addPostContent(contents);

        postRepository.save(post);

        return post;
    }

    @Transactional
    public Post updatePost(Long postId, PostForm postForm) {
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
                    .map(contentForm -> PostContent.createPostContent(
                            contentForm.getType(),
                            contentForm.getValue(),
                            contentForm.getOrderIndex(),
                            post))
                    .collect(Collectors.toList());

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

    // ✅ 1️⃣ 모바일 - 첫 페이지 불러오기 (커서 없음)
    public List<Post> findFirstPage(String category, int size, String sort) {
        return sort.equalsIgnoreCase("인기순")
                ? postRepository.findPopularPostsByCategory(category, size)
                : postRepository.findLatestPostsByCategory(category, size);
    }

    // ✅ 2️⃣ 모바일 - 추가 데이터 로딩 (커서 이후 데이터)
    public List<Post> findNextPage(String category, Long cursorLikes, Long cursorId, int size, String sort) {
        return sort.equalsIgnoreCase("인기순")
                ? postRepository.findPopularPostsByCategoryWithCursor(category, cursorLikes+1, cursorId, size)
                : postRepository.findLatestPostsByCategoryWithCursor(category, cursorId, size);
    }

    // ✅ 3️⃣ PC - 일반 페이지네이션 적용 (15개씩 불러오기)
    public List<Post> findPagedPosts(String category, int pageNum, int size, String sort) {
        Pageable pageable = PageRequest.of(pageNum, size);
        return sort.equalsIgnoreCase("인기순")
                ? postRepository.findPopularPostsByCategoryPaged(category, pageable).getContent()
                : postRepository.findLatestPostsByCategoryPaged(category, pageable).getContent();
    }

}