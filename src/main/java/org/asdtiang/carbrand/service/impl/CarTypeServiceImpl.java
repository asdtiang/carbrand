package org.asdtiang.carbrand.service.impl;

import org.asdtiang.carbrand.service.CarTypeService;
import org.asdtiang.carbrand.domain.CarType;
import org.asdtiang.carbrand.repository.CarTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CarType}.
 */
@Service
@Transactional
public class CarTypeServiceImpl implements CarTypeService {

    private final Logger log = LoggerFactory.getLogger(CarTypeServiceImpl.class);

    private final CarTypeRepository carTypeRepository;

    public CarTypeServiceImpl(CarTypeRepository carTypeRepository) {
        this.carTypeRepository = carTypeRepository;
    }

    /**
     * Save a carType.
     *
     * @param carType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CarType save(CarType carType) {
        log.debug("Request to save CarType : {}", carType);
        return carTypeRepository.save(carType);
    }

    /**
     * Get all the carTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarType> findAll(Pageable pageable) {
        log.debug("Request to get all CarTypes");
        return carTypeRepository.findAll(pageable);
    }


    /**
     * Get one carType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarType> findOne(Long id) {
        log.debug("Request to get CarType : {}", id);
        return carTypeRepository.findById(id);
    }

    /**
     * Delete the carType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarType : {}", id);
        carTypeRepository.deleteById(id);
    }
}
