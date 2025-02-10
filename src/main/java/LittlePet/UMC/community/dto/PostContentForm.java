package LittlePet.UMC.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostContentForm {

    private String type; // test, image
    @NotBlank
    private String value;
    private int orderIndex;
}
