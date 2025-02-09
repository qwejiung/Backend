package LittlePet.UMC.community.dto;

import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import LittlePet.UMC.domain.userEntity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {

    //댓글 단 사람의 닉네임, 펫 종류, 성별, 그, 댓글내용, 댓글 생성 시간
    private String name;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime updatedTime;

    private List<String> userPets;
    private List<RepliesDTO> replies;

    public CommentResponseDTO(Comment comment) {
        this.name = comment.getUser().getName();
        this.content = comment.getContent();
        this.createdTime = comment.getCreatedAt();
        this.updatedTime = comment.getUpdatedAt();
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
