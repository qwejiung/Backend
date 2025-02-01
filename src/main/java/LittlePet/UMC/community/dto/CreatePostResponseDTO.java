package LittlePet.UMC.community.dto;

import LittlePet.UMC.domain.postEntity.Post;
import lombok.Data;

@Data
public class CreatePostResponseDTO {
    private String title;
    private String postCategory; //postCategory
    private String petCategory;
    private String content;

    public CreatePostResponseDTO(Post post,String petCategory) {
        title = post.getTitle();
        postCategory = post.getPostCategory().getCategory();
        this.petCategory = petCategory;
        content = post.getPostcontentList().get(0).getContent();
    }
}

