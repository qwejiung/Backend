package LittlePet.UMC.community.controller;

import LittlePet.UMC.S3Service;
import LittlePet.UMC.SmallPet.service.PetCategoryService;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.community.dto.*;
import LittlePet.UMC.community.service.PostService;
import LittlePet.UMC.domain.postEntity.Post;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PetCategoryService petCategoryService;
    private final S3Service s3Service;

    @Operation(summary = "커뮤니티 게시물 생성", description = "커뮤니티에 새로운 게시물을 생성할 수 있는 카테고리입니다")
    @PostMapping(value = "/post/{user-id}", consumes = {"multipart/form-data"})
    public ApiResponse<CreatePostResponseDTO> createPost(
            @RequestPart @Valid PostForm postForm,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @PathVariable("user-id") Long userId) throws IOException {

//        if (images.size() > 5) {
//             // 예외 발생
//        }

        int num = 0;
        for (PostContentForm form : postForm.getContents() ) {
            if ( form.getType().equals("image") && num < images.size() ) {
                String url = s3Service.upload(images.get(num));
                form.setValue(url);
                num++;
            }
        }

        Post post = postService.createPost(postForm, userId);

        CreatePostResponseDTO dto = new CreatePostResponseDTO(post);

        return ApiResponse.onSuccess(dto);
    }

    @Operation(summary = "커뮤니티 게시물 수정", description = "커뮤니티에 기존 게시물을 수정할 수 있는 카테고리입니다")
    @PatchMapping(value = "/post/{post-id}", consumes = {"multipart/form-data"})
    public ApiResponse<CreatePostResponseDTO> updatePost(
            @PathVariable("post-id") Long postId,
            @RequestPart @Valid PostForm postForm,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        int num = 0;
        for (PostContentForm form : postForm.getContents() ) {
            if ( form.getType().equals("image") && num < images.size() ) {
                String url = s3Service.upload(images.get(num));
                form.setValue(url);
                num++;
            }
        }

        Post updatePost = postService.updatePost(postId, postForm);

        CreatePostResponseDTO dto = new CreatePostResponseDTO(updatePost);

        return ApiResponse.onSuccess(dto);
    }

    @Operation(summary = "커뮤니티 게시물 삭제", description = "커뮤니티를 삭제할 수 있는 카테고리입니다")
    @DeleteMapping("/post/{post-id}")
    public ApiResponse<String> deletePost(@PathVariable("post-id") Long postId) {
        postService.deletePost(postId);
        return ApiResponse.onSuccess("게시물이 성공적으로 삭제되었습니다");
    }

    @Operation(summary = "커뮤니티 특정 글 조회", description = "특정 게시물을 조회하는 카테고리입니다")
    @GetMapping("/post/{post-id}")
    public ApiResponse<GetPostResponseDTO> getPost(
            @PathVariable("post-id") Long postId,
            @RequestParam(value = "deviceType", defaultValue = "pc") String deviceType) {
        Post post = postService.FindOnePost(postId);

        return ApiResponse.onSuccess(new GetPostResponseDTO(post,deviceType));
    }

    @Operation(summary = "커뮤니티 글 목록 조회", description = "커뮤니티의 게시판을 조회하는 카테고리입니다")
    @GetMapping("/post")
    public ApiResponse<List<GetAllPostResponseDTO>> getPosts(
            @RequestParam String category, //Q&A
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "최신순") String sort,
            @RequestParam(defaultValue = "0", required = false) Long cursorLikes,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(value = "deviceType", defaultValue = "pc") String deviceType) {

        List<Post> posts;

        if ("mobile".equalsIgnoreCase(deviceType)) {
            //모바일
            posts = (cursorLikes == null || cursorId == null)
                    ? postService.findFirstPage(category, size, sort)  // 첫 페이지 (커서 없음)
                    : postService.findNextPage(category, cursorLikes, cursorId, size, sort); // 커서 이후 데이터 로딩
        } else {
            //pc
            posts = postService.findPagedPosts(category, pageNum, size, sort);
        }

        List<GetAllPostResponseDTO> dtos = GetAllPostResponseDTO.fromEntityList(posts);
        return ApiResponse.onSuccess(dtos);
    }
}

