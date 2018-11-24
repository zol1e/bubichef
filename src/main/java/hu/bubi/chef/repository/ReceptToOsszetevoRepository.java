package hu.bubi.chef.repository;

import hu.bubi.chef.domain.ReceptToOsszetevo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReceptToOsszetevo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceptToOsszetevoRepository extends JpaRepository<ReceptToOsszetevo, Long> {

}
