package LittlePet.UMC.community.repository.postRepository;

import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT COUNT(p) FROM Post p WHERE p.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.user.id = :userId")
    long getTotalLikesReceivedByUserId(@Param("userId") Long userId);

    Page<Post> findByPostCategory_Category(String category, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "JOIN p.postCategory pc " +
            "WHERE pc.category = :category " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findLatestPostsByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "JOIN p.postCategory pc " +
            "WHERE pc.category = :category " +
            "AND (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) >= 30 " +
            "ORDER BY (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) DESC")
    Page<Post> findPopularPostsByCategory(@Param("category") String category, Pageable pageable);
}
