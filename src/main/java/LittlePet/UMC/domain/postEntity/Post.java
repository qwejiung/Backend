package LittlePet.UMC.domain.postEntity;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import LittlePet.UMC.domain.postEntity.mapping.PostClipping;
import LittlePet.UMC.domain.postEntity.mapping.PostContent;
import LittlePet.UMC.domain.userEntity.User;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "BIGINT default 0")
    private Long views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postcategory_id", nullable = false)
    private PostCategory postCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_category_id", nullable = false)
    private PetCategory petCategory;       //게시물의 성별이 필요한 것으로 판단 PetCategory -> UserPet

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList= new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLikeList= new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostContent> postcontentList= new ArrayList<>();
    // Getters, Setters, Constructors
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostClipping> postClippingList= new ArrayList<>();

    @Transient // DB에는 저장되지 않음
    private int sequenceCounter = 1;

    public static Post createPost(String title, long views, User user, PostCategory postCategory, PetCategory petCategory) {
        Post post = new Post();
        post.setTitle(title);
        post.setUser(user);
        post.setViews(views);
        post.setPostCategory(postCategory);
        post.setPetCategory(petCategory);

        return post;
    }

    public void addPostContent(List<PostContent> contents) {
        for (PostContent content : contents) {
            this.postcontentList.add(content);
        }
    }

    public Integer getSequenceCounter() {
        return sequenceCounter++;
    }

    public void resetSequenceCounter() {
        sequenceCounter = 1;
    }

    public long getTotalCommentCount() {
        long totalCount = commentList.size(); // ✅ 최상위 댓글 개수 포함

        for (Comment comment : commentList) {
            totalCount += comment.getTotalReplyCount(); // ✅ 각 댓글의 대댓글 개수 합산
        }

        return totalCount;
    }
}
