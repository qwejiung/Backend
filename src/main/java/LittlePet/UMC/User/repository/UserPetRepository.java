package LittlePet.UMC.User.repository;

import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPetRepository extends JpaRepository<UserPet, Long> {
    Optional<UserPet> findByIdAndUserId(Long petId, Long userId);
}
