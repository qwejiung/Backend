package LittlePet.UMC.community.repository.postRepository;

import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    //
    // ✅ 모바일 - 최신순 첫 페이지
    @Query("SELECT p FROM Post p " +
            "JOIN p.postCategory pc " +
            "WHERE pc.category = :category " +
            "ORDER BY p.createdAt DESC " +
            "LIMIT :size")
    List<Post> findLatestPostsByCategory(@Param("category") String category, @Param("size") int size);

    // ✅ 모바일 - 최신순 커서 이후 데이터 로드
    @Query("SELECT p FROM Post p " +
            "JOIN p.postCategory pc " +
            "WHERE pc.category = :category " +
            "AND p.id < :cursor " +
            "ORDER BY p.createdAt DESC " +
            "LIMIT :size")
    List<Post> findLatestPostsByCategoryWithCursor(@Param("category") String category, @Param("cursor") Long cursor, @Param("size") int size);

    // ✅ 모바일 - 인기순 첫 페이지
    @Query("SELECT p FROM Post p " +
            "JOIN p.postCategory pc " +
            "WHERE pc.category = :category " +
            "AND (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) >= 30 " +
            "ORDER BY (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) DESC " +
            "LIMIT :size")
    List<Post> findPopularPostsByCategory(@Param("category") String category, @Param("size") int size);

    // ✅ 모바일 - 인기순 커서 이후 데이터 로드
    @Query("SELECT p FROM Post p " +
            "JOIN p.postCategory pc " +
            "WHERE pc.category = :category " +
            "AND (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) >= 30 " +
            "AND ((SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) < :cursorLikes " +
            "    OR ((SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) = :cursorLikes AND p.id < :cursorId)) " +
            "ORDER BY (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) DESC, p.id DESC " +
            "LIMIT :size")
    List<Post> findPopularPostsByCategoryWithCursor(
            @Param("category") String category,
            @Param("cursorLikes") Long cursorLikes,  // 커서 좋아요 개수
            @Param("cursorId") Long cursorId,        // 커서 ID
            @Param("size") int size);

    // ✅ PC - 최신순 페이지네이션
    @Query("SELECT p FROM Post p " +
            "JOIN p.postCategory pc " +
            "WHERE pc.category = :category " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findLatestPostsByCategoryPaged(@Param("category") String category, Pageable pageable);

    // ✅ PC - 인기순 페이지네이션
    @Query("SELECT p FROM Post p " +
            "JOIN p.postCategory pc " +
            "WHERE pc.category = :category " +
            "AND (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) >= 30 " +
            "ORDER BY (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post = p) DESC, p.id DESC")
    Page<Post> findPopularPostsByCategoryPaged(@Param("category") String category, Pageable pageable);
}
