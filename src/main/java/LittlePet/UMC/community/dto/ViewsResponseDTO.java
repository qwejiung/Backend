package LittlePet.UMC.community.dto;

import LittlePet.UMC.domain.postEntity.Post;
import lombok.Data;

@Data
public class ViewsResponseDTO {

    private long postId;
    private long views;

    public ViewsResponseDTO(Post post) {
        this.postId = getPostId();
        this.views = post.getViews();
    }
}
