package LittlePet.UMC.community.repository;

import LittlePet.UMC.domain.postEntity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    Optional<PostCategory> findByCategory(String cateGory);
}
