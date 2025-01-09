package LittlePet.spring.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
