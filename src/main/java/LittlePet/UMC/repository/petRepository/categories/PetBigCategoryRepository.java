package LittlePet.UMC.repository.petRepository.categories;

import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.postEntity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetBigCategoryRepository extends JpaRepository<PetBigCategory, Long>  {
}
