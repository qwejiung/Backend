package LittlePet.UMC.community.repository.postRepository;

import LittlePet.UMC.domain.postEntity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
