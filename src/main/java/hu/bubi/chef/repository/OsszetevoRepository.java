package hu.bubi.chef.repository;

import hu.bubi.chef.domain.Osszetevo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Osszetevo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OsszetevoRepository extends JpaRepository<Osszetevo, Long> {

}
