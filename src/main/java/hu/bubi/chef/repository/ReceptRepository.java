package hu.bubi.chef.repository;

import hu.bubi.chef.domain.Recept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Recept entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceptRepository extends JpaRepository<Recept, Long> {

    @Query(value = "select distinct recept from Recept recept left join fetch recept.hashtageks",
        countQuery = "select count(distinct recept) from Recept recept")
    Page<Recept> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct recept from Recept recept left join fetch recept.hashtageks")
    List<Recept> findAllWithEagerRelationships();

    @Query("select recept from Recept recept left join fetch recept.hashtageks where recept.id =:id")
    Optional<Recept> findOneWithEagerRelationships(@Param("id") Long id);

}
