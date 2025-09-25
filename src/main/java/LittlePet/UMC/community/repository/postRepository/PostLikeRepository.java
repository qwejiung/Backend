package LittlePet.UMC.community.repository.postRepository;

import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<PostLike> findByUserIdAndPostId(Long userId, Long postId);

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

    @Query(value = """
    SELECT ranking
    FROM (
        SELECT 
            po.user_id,
            COUNT(p.id) AS like_count,
            DENSE_RANK() OVER (ORDER BY COUNT(p.id) DESC) AS ranking
        FROM post_like p
        JOIN post po ON p.post_id = po.id
        JOIN post_category pc ON po.postcategory_id = pc.id
        WHERE pc.category = '챌린지'
        GROUP BY po.user_id
    ) ranked
    WHERE user_id = :userId
    """, nativeQuery = true)
    Long findMyRankingByChallenge(@Param("userId") Long userId);

    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.id = :postId")
    int countPostLikes(@Param("postId") Long postId);


}
