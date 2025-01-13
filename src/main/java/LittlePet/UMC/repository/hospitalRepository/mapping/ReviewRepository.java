package LittlePet.UMC.repository.hospitalRepository.mapping;

import LittlePet.UMC.domain.hospitalEntity.mapping.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
