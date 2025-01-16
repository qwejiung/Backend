package LittlePet.UMC.repository.hospitalRepository.mapping;

import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalStarRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<HospitalStarRating, Long> {
}
