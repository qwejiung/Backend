package LittlePet.UMC.repository.hospitalRepository.mapping;

import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalPrefRepository extends JpaRepository<HospitalPref, Integer> {
}
