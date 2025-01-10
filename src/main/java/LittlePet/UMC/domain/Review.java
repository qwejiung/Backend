package LittlePet.UMC.domain;

import LittlePet.UMC.domain.Auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter @Setter @ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String content;
    private Double rating;
    private String petGender;

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
