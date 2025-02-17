package LittlePet.UMC.community.dto;

import LittlePet.UMC.domain.postEntity.mapping.PostContent;
import lombok.Data;

@Data
public class PostContentResponseDTO {
    private Long postContentId;
    private String content;
    private int sequence;

    public PostContentResponseDTO(PostContent postContent) {
        this.postContentId = postContent.getId();
        this.content = postContent.getContent();
        this.sequence = postContent.getSequence();
    }
}
