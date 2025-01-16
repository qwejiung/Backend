package LittlePet.UMC.domain.BadgeEntity;

import LittlePet.UMC.domain.BaseEntity.BaseTimeEntity;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class Badge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    //이렇게 조회하는 경우는 없음.
//    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
//    private List<UserBadge> healthRecordList= new ArrayList<>();

}
