package LittlePet.UMC.community.dto.commentDTO;

import LittlePet.UMC.domain.postEntity.mapping.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;
    private Long parentId;
    private List<Comment> commentList;

    public static CommentResponseDTO of(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getPost() != null ? comment.getPost().getId() : null,
                comment.getParent() != null ? comment.getParent().getId() : null,
                comment.getPost() != null ? comment.getPost().getCommentList() : null
        );
    }
}
