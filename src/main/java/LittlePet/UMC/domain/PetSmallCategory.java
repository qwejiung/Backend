package LittlePet.UMC.domain;

import LittlePet.UMC.domain.Auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter @Setter @ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class PetSmallCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String petname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BigCategory_Id")
    private PetBigCategory bigcategory;

    @OneToOne(mappedBy = "smallcategory", cascade = CascadeType.ALL)
    private Petinfo petinfo;

    @OneToMany(mappedBy = "petsmallcategory", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    // Getters and Setters
}
