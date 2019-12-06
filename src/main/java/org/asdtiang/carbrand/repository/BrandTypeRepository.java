package org.asdtiang.carbrand.repository;
import org.asdtiang.carbrand.domain.BrandType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BrandType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandTypeRepository extends JpaRepository<BrandType, Long> {

}
