package LittlePet.UMC.SmallPet.repository;

import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetBigCategoryRepository extends JpaRepository<PetBigCategory, Long> {
}
