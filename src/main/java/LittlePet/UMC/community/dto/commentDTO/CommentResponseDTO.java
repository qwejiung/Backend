package LittlePet.UMC.community.dto.commentDTO;

import LittlePet.UMC.domain.postEntity.mapping.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;
    private Long parentId;

    public static CommentResponseDTO of(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getPost() != null ? comment.getPost().getId() : null,
                comment.getParent() != null ? comment.getParent().getId() : null
        );
    }
}
