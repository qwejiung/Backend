package LittlePet.UMC.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String content;
    private Double rating;
    private String petGender;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    //병원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hopital_Id")
    private Hospital hospital;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewMedia> reviewmediaList= new ArrayList<>();


    // Getters, Setters, Constructors
}
