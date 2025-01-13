package LittlePet.UMC.repository.petRepository.mapping;

import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.postEntity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
}
