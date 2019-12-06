package org.asdtiang.carbrand.service;

import org.asdtiang.carbrand.domain.CarType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CarType}.
 */
public interface CarTypeService {

    /**
     * Save a carType.
     *
     * @param carType the entity to save.
     * @return the persisted entity.
     */
    CarType save(CarType carType);

    /**
     * Get all the carTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarType> findAll(Pageable pageable);


    /**
     * Get the "id" carType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarType> findOne(Long id);

    /**
     * Delete the "id" carType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
