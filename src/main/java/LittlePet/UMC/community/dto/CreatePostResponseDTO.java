package LittlePet.UMC.community.dto;

import LittlePet.UMC.domain.postEntity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponseDTO {
    private String title;
    private String postCategory; //postCategory
    private String petCategory;
    private String content;

    public CreatePostResponseDTO(Post post) {
        title = post.getTitle();
        postCategory = post.getPostCategory().getCategory();
        this.petCategory = post.getPetCategory().getSpecies();
        content = post.getPostcontentList().get(0).getContent();
    }
}

