package LittlePet.UMC.community.Converter;

import LittlePet.UMC.community.dto.postlikeDTO.PostLikeResponseDTO;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;

import java.time.LocalDateTime;

public class PostLikeConverter {

    public static PostLikeResponseDTO.postlikeResultDTO toPostResponseDTO(PostLike postLike){

        return PostLikeResponseDTO.postlikeResultDTO.builder()
                .userId(postLike.getUser().getId())
                .postId(postLike.getPost().getId())
                .createdAt(LocalDateTime.now())
                .build();

    }
}
