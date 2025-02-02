package LittlePet.UMC.community.controller.commentController;

import LittlePet.UMC.community.dto.commentDTO.CommentRequestDTO;
import LittlePet.UMC.community.dto.commentDTO.CommentResponseDTO;
import LittlePet.UMC.community.service.commentService.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community/{postId}/comments")
@RequiredArgsConstructor
@Tag(name = "댓글 API", description = "게시물 댓글 및 대댓글 관리 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "parentId가 있으면 대댓글, 없으면 일반 댓글을 작성합니다.")
    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO responseDTO = commentService.createComment(postId, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO responseDTO = commentService.updateComment(commentId, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

}
