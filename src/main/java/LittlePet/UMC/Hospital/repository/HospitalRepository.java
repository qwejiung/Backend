package LittlePet.UMC.Hospital.repository;

import LittlePet.UMC.domain.hospitalEntity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    // 지역구에 해당하는 병원 목록을 검색
    List<Hospital> findHospitalsByPlaceId(Long placeId);
}
