package LittlePet.UMC.HealthRecord.repository;

import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {

    /**
     * 특정 반려동물의 최신 건강 기록 조회
     */
    Optional<HealthRecord> findFirstByUserPetOrderByRecordDateDesc(UserPet userPet);
    Optional<HealthRecord> findByUserPetAndRecordDate(UserPet userPet, LocalDate recordDate);
    List<HealthRecord> findAllByUserPet(UserPet pet);
    Optional<HealthRecord> findFirstByUserPetAndRecordDateBeforeOrderByRecordDateDesc(UserPet userPet, LocalDate recordDate);


    void deleteByUserPet(UserPet userPet);  // JPA 자동 삭제 지원 (JPQL 불필요)

}
