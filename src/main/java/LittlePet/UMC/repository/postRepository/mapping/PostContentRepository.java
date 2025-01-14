package LittlePet.UMC.repository.postRepository.mapping;

import LittlePet.UMC.domain.postEntity.mapping.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostContentRepository extends JpaRepository<PostContent, Long> {
}
