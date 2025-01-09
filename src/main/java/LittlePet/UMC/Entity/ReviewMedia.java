package LittlePet.spring.Entity;
import java.util.Date;
import jakarta.persistence.*;

@Entity
public class ReviewMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewMediaId;

    private String mediaUrl;
    private String mediaType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "review_Id")
    private Review review;

    // Getters, Setters, Constructors
}