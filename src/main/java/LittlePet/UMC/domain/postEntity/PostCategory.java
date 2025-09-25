package LittlePet.UMC.domain.postEntity;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class PostCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "postCategory", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Post> postList= new ArrayList<>();

    public PostCategory(String category) {
        this.category = category;
    }
}
