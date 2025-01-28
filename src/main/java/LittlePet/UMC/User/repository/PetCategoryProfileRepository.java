package LittlePet.UMC.User.repository;

import LittlePet.UMC.domain.petEntity.categories.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetCategoryProfileRepository extends JpaRepository<PetCategory,Long> {

}
