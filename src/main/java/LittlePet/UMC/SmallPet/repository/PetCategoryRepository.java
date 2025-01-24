package LittlePet.UMC.SmallPet.repository;

import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetCategoryRepository extends JpaRepository<PetCategory, Integer> {
}
