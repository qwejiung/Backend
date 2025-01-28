package LittlePet.UMC.community.repository;

import LittlePet.UMC.domain.postEntity.PostCategory;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import LittlePet.UMC.domain.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLlikeRepository extends JpaRepository<PostLike,Long> {
    @Query("SELECT COUNT(l) FROM PostLike l WHERE l.user.id = :userId")
    long getCountByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT po.user_id FROM post_like p " +
            "JOIN post po ON p.post_id = po.id " +
            "JOIN post_category pc ON po.postcategory_id = pc.id " +
            "WHERE pc.category = '챌린지' " +
            "GROUP BY p.user_id " +
            "ORDER BY COUNT(p.id) DESC " +
            "LIMIT 3", nativeQuery = true)
    List<Long> findTopUsersByChallenge();

}
