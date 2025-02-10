package LittlePet.UMC.community.repository.postlikerepository;

import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
