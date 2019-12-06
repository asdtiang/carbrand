package org.asdtiang.carbrand.service;

import org.asdtiang.carbrand.domain.BrandType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BrandType}.
 */
public interface BrandTypeService {

    /**
     * Save a brandType.
     *
     * @param brandType the entity to save.
     * @return the persisted entity.
     */
    BrandType save(BrandType brandType);

    /**
     * Get all the brandTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BrandType> findAll(Pageable pageable);


    /**
     * Get the "id" brandType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BrandType> findOne(Long id);

    /**
     * Delete the "id" brandType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
