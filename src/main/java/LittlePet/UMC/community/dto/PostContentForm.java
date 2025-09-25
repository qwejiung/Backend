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

    private Long id;
    private String type; // test, image
    private String value;

    private String status; //url 넣을때만 값 전달
    private Integer orderIndex;
}
