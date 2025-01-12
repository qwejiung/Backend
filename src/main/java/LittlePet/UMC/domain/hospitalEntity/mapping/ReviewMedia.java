package LittlePet.UMC.domain.hospitalEntity.mapping;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.enums.MediaTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class ReviewMedia extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mediaUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaTypeEnum mediaType;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    // Getters, Setters, Constructors
}