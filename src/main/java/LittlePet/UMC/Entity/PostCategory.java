package LittlePet.spring.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @OneToMany(mappedBy = "postcategory", cascade = CascadeType.ALL)
    private List<Post > postList= new ArrayList<>();

    // Getters, Setters, Constructors
}
