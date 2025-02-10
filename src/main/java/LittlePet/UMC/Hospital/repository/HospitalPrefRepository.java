package LittlePet.UMC.Hospital.repository;

import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalPrefRepository extends JpaRepository<HospitalPref, Long> {

    boolean existsByUserAndHospital(User user, Hospital hospital);

    Optional<HospitalPref> findByUserAndHospital(User user, Hospital hospital);

    List<HospitalPref> findByUser(User user);

}
