package LittlePet.UMC.community.dto;

import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class RepliesDTO {
    private String name;
    private String content;
    private LocalDateTime createdTime;
    private List<String> userPets;
    private List<RepliesDTO> replies;

    public RepliesDTO(Comment comment) {
        this.name = comment.getUser().getName();
        this.content = comment.getContent();
        this.createdTime = comment.getCreatedAt();
        this.userPets = Optional.ofNullable(comment.getUser().getUserPetList())
                .orElse(Collections.emptyList())
                .stream()
                .map(userPet -> userPet.getPetCategory().getSpecies())
                .distinct()
                .collect(Collectors.toList());
        this.replies = Optional.ofNullable(comment.getReplies())
                .orElse(Collections.emptyList()) // null이면 빈 리스트 반환
                .stream()
                .map(reply -> new RepliesDTO(reply))
                .collect(Collectors.toList());
    }
}
