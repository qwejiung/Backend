package LittlePet.UMC.SmallPet.repository;

import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, Long> {
}
