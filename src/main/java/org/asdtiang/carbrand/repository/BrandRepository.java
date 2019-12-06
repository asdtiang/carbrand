package org.asdtiang.carbrand.repository;
import org.asdtiang.carbrand.domain.Brand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Brand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
