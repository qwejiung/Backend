package LittlePet.UMC.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class PetSmallCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PetName")
    private String petname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BigCategory_Id")
    private PetBigCategory bigcategory;

    @OneToOne(mappedBy = "smallcategory", cascade = CascadeType.ALL)
    private Petinfo petinfo;

    @OneToMany(mappedBy = "smallcategory", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    // Getters and Setters
}
