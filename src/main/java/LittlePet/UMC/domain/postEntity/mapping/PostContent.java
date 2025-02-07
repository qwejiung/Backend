package LittlePet.UMC.domain.postEntity.mapping;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.enums.MediaTypeEnum;
import LittlePet.UMC.domain.postEntity.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class PostContent extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaTypeEnum mediaType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer sequence;       //블로그 형식처럼 글 -> 사진 -> 글 하기위한 순서번호

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    public static PostContent createPostContent(String content, String imageUrl, Post post) {

        PostContent postContent = new PostContent();

        // 이미지 URL이 존재하면 content 필드에 저장
        if (imageUrl != null && !imageUrl.isEmpty()) {
            postContent.setMediaType(MediaTypeEnum.Picture);
            postContent.setContent(imageUrl);
        } else {
            postContent.setMediaType(MediaTypeEnum.Text);
            postContent.setContent(content);
        }

        postContent.setSequence(post.getSequenceCounter());
        postContent.setPost(post);

        return postContent;
    }
}
