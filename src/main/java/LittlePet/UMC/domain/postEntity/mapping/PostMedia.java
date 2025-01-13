package LittlePet.UMC.domain.postEntity.mapping;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.enums.MediaTypeEnum;
import LittlePet.UMC.domain.postEntity.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class PostMedia extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaTypeEnum mediaType;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
