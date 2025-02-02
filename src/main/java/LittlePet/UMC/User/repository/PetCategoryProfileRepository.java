package LittlePet.UMC.User.repository;

import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetCategoryProfileRepository extends JpaRepository<PetCategory,Long> {
    Optional<PetCategory> findBySpecies(String species);
}
