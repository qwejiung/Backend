package LittlePet.UMC.Badge.repository.badgeRepository;

import LittlePet.UMC.domain.BadgeEntity.Badge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BadgeRepositoryImpl implements BadgeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Badge findByBadgeType(String badgeType) {
        String jpql = "SELECT b FROM Badge b WHERE b.name = :badgeType";
        Badge badge = entityManager.createQuery(jpql, Badge.class)
                .setParameter("badgeType", badgeType)
                .getSingleResult();
        return badge;
    }



}
