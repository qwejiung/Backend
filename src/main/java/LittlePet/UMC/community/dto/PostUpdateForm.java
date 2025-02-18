package LittlePet.UMC.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateForm {
    private String title; // ✅ 게시물 제목 수정 가능하도록 추가
    private String postCategory;
    private String smallPetCategory;
    private List<PostContentForm> added = new ArrayList<>();
    private List<PostContentUpdateForm> updated = new ArrayList<>();
    private List<Long> deleted = new ArrayList<>();
}