package LittlePet.spring.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//소동물 대분류 - 설치류 , 파충류
@Entity
public class PetBigCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryname;

    @OneToMany(mappedBy = "bigcategory", cascade = CascadeType.ALL)
    private List<PetSmallCategory> smallcategories = new ArrayList<>();

    // Getters and Setters
}
