package LittlePet.UMC.repository.postRepository;

import LittlePet.UMC.domain.postEntity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
