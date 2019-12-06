package org.asdtiang.carbrand.service.impl;

import org.asdtiang.carbrand.service.BrandTypeService;
import org.asdtiang.carbrand.domain.BrandType;
import org.asdtiang.carbrand.repository.BrandTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BrandType}.
 */
@Service
@Transactional
public class BrandTypeServiceImpl implements BrandTypeService {

    private final Logger log = LoggerFactory.getLogger(BrandTypeServiceImpl.class);

    private final BrandTypeRepository brandTypeRepository;

    public BrandTypeServiceImpl(BrandTypeRepository brandTypeRepository) {
        this.brandTypeRepository = brandTypeRepository;
    }

    /**
     * Save a brandType.
     *
     * @param brandType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BrandType save(BrandType brandType) {
        log.debug("Request to save BrandType : {}", brandType);
        return brandTypeRepository.save(brandType);
    }

    /**
     * Get all the brandTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandType> findAll(Pageable pageable) {
        log.debug("Request to get all BrandTypes");
        return brandTypeRepository.findAll(pageable);
    }


    /**
     * Get one brandType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BrandType> findOne(Long id) {
        log.debug("Request to get BrandType : {}", id);
        return brandTypeRepository.findById(id);
    }

    /**
     * Delete the brandType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BrandType : {}", id);
        brandTypeRepository.deleteById(id);
    }
}
