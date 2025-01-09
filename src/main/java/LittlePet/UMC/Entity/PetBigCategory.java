package LittlePet.UMC.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//소동물 대분류 - 설치류 , 파충류
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
