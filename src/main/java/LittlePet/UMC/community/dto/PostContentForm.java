package LittlePet.UMC.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostContentForm {

    @NotBlank
    private String content;
}
