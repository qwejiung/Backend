package LittlePet.UMC.repository.postRepository.mapping;

import LittlePet.UMC.domain.postEntity.mapping.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
