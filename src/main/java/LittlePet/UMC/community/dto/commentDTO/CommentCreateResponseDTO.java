package LittlePet.UMC.community.dto.commentDTO;

import LittlePet.UMC.community.dto.CommentResponseDTO;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CommentCreateResponseDTO {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;
    private Long parentId;
    private List<CommentResponseDTO> commentList;  // 댓글 목록

    public static CommentCreateResponseDTO of(Comment comment ) {
        List<CommentResponseDTO> commentList  = comment.getPost().getCommentList().stream()
                .map(CommentResponseDTO::new) // CommentResponseDTO로 변환
                .collect(Collectors.toList());


        return new CommentCreateResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getPost() != null ? comment.getPost().getId() : null,
                comment.getParent() != null ? comment.getParent().getId() : null,
                commentList
        );
    }
}
