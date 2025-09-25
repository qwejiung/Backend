package LittlePet.UMC.community.dto.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDTO{
    private String content;
    private Long userId;
    private Long postId;
    private Long parentId;
}
