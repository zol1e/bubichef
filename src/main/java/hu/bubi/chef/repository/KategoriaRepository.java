package hu.bubi.chef.repository;

import hu.bubi.chef.domain.Kategoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Kategoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KategoriaRepository extends JpaRepository<Kategoria, Long> {

}
