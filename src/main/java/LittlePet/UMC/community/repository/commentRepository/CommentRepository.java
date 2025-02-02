package LittlePet.UMC.community.repository.commentRepository;

import LittlePet.UMC.domain.postEntity.mapping.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.user.id = :userId")
    long getCountByUserId(@Param("userId") Long userId);

}
