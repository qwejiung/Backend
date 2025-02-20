package LittlePet.UMC.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostForm {

    private String title;
    private String smallPetCategory; //petCategory
    private String postCategory; //postCategory
    private List<PostContentForm> contents = new ArrayList<>();

}