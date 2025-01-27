package LittlePet.UMC.SmallPet.repository;

import LittlePet.UMC.domain.petEntity.mapping.UserPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmallPetRepository extends JpaRepository<UserPet , Long> {
}
