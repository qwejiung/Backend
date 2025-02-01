package LittlePet.UMC.SmallPet.repository;

import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetBigCategoryRepository extends JpaRepository<PetBigCategory, Long> {
    Optional<PetBigCategoryRepository> findByCategoryName(String categoryName);
}
