package LittlePet.UMC.community.controller;

import LittlePet.UMC.S3Service;
import LittlePet.UMC.SmallPet.service.PetCategoryService;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.community.dto.CreatePostResponseDTO;
import LittlePet.UMC.community.dto.PostForm;
import LittlePet.UMC.community.service.PostService;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.postEntity.Post;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable("user-id") Long userId) throws IOException {

        String url = s3Service.upload(image);
        Post post = postService.createPost(postForm, userId, url);

        CreatePostResponseDTO dto = new CreatePostResponseDTO(post,postForm.getSmallPetCategory());

        return ApiResponse.onSuccess(dto);
    }

    @Operation(summary = "커뮤니티 게시물 수정", description = "커뮤니티에 기존 게시물을 수정할 수 있는 카테고리입니다")
    @PatchMapping(value = "/post/{post-id}", consumes = {"multipart/form-data"})
    public ApiResponse<CreatePostResponseDTO> updatePost(
            @PathVariable("post-id") Long postId,
            @RequestPart @Valid PostForm postForm,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        String url = s3Service.upload(image);
        Post updatePost = postService.updatePost(postId, postForm,url);

        CreatePostResponseDTO dto = new CreatePostResponseDTO(updatePost,postForm.getSmallPetCategory());

        return ApiResponse.onSuccess(dto);
    }

    @Operation(summary = "커뮤니티 게시물 삭제", description = "커뮤니티를 삭제할 수 있는 카테고리입니다")
    @DeleteMapping("/post/{post-id}")
    public ApiResponse<String> deletePost(@PathVariable("post-id") Long postId) {
        postService.deletePost(postId);
        return ApiResponse.onSuccess("게시물이 성공적으로 삭제되었습니다");
    }
}

