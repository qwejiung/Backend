package LittlePet.UMC.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostContentUpdateForm {
    private Long id;
    private String type;
    private String value; // 이미지의 경우 null일 수도 있음
    private Integer orderIndex;
}