package LittlePet.UMC.community.dto;

import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.PostContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponseDTO {
    private long id;
    private String title;
    private String postCategory; //postCategory
    private String petCategory;
    private List<PostContentResponseDTO> contents;

    public CreatePostResponseDTO(Post post) {
        this.id = post.getId();
        title = post.getTitle();
        postCategory = post.getPostCategory().getCategory();
        this.petCategory = post.getPetCategory().getSpecies();
        this.contents = Optional.ofNullable(post.getPostcontentList())
                .orElse(Collections.emptyList())  // null이면 빈 리스트 반환
                .stream()
                .sorted(Comparator.comparingInt(PostContent::getSequence))
                .map(postContent -> new PostContentResponseDTO(postContent))
                .collect(Collectors.toList());
    }
}

