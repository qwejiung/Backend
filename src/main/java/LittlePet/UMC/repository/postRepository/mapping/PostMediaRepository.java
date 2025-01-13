package LittlePet.UMC.repository.postRepository.mapping;

import LittlePet.UMC.domain.postEntity.mapping.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {
}
