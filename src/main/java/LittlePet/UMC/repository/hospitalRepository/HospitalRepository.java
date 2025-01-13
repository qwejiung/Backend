package LittlePet.UMC.repository.hospitalRepository;

import LittlePet.UMC.domain.hospitalEntity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
}
