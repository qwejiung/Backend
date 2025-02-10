package LittlePet.UMC.community.service.postlikeService;

import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import jakarta.transaction.Transactional;

public interface PostLikeCommandService {
    PostLike addlike(Long userId, Long postId);


}
