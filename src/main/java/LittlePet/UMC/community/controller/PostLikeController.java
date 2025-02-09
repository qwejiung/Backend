package LittlePet.UMC.community.controller;


import LittlePet.UMC.Badge.validation.annotation.ExistBadgeType;
import LittlePet.UMC.Badge.validation.annotation.ExistUser;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.community.Converter.PostLikeConverter;
import LittlePet.UMC.community.dto.postlikeDTO.PostLikeResponseDTO;
import LittlePet.UMC.community.service.postlikeService.PostLikeCommandService;
import LittlePet.UMC.community.validation.annotation.ExistPost;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
@Validated
public class PostLikeController {

    private final PostLikeCommandService postLikeCommandService;

    /**
     * 게시물의 좋아요 등록 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @param postId 게시물 ID (PathVariable)
     * @return 등록한 유저뱃지 응답 DTO
     */
    @Operation(summary = "좋아요 등록", description = "게시물의 좋아요를 누르는 API 입니다.")
    @PostMapping("/{userId}/{postId}")
    public ApiResponse<PostLikeResponseDTO.postlikeResultDTO> click_like(
            @PathVariable @ExistUser Long userId,
            @PathVariable @ExistPost Long postId) //@ExistPost로 바꿔야함
    {
        PostLike postLike = postLikeCommandService.addlike(userId,postId);
        return ApiResponse.onSuccess(PostLikeConverter.toPostResponseDTO(postLike));
    }
}
