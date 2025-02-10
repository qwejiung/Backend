package LittlePet.UMC.community.dto;

import lombok.Data;

@Data
public class PostContentResponseDTO {
    private String content;
    private int sequence;

    public PostContentResponseDTO(String content, int sequence) {
        this.content = content;
        this.sequence = sequence;
    }
}
