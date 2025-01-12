package LittlePet.UMC.repository.hospitalRepository.mapping;

import LittlePet.UMC.domain.hospitalEntity.mapping.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
}
