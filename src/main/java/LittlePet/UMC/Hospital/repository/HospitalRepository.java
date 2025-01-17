package LittlePet.UMC.Hospital.repository;

import LittlePet.UMC.domain.hospitalEntity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
