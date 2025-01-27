package LittlePet.UMC.Hospital.repository;

import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalStarRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalStarRatingRepository extends JpaRepository<HospitalStarRating, Long> {
}
