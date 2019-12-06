package org.asdtiang.carbrand.web.rest;

import org.asdtiang.carbrand.domain.BrandType;
import org.asdtiang.carbrand.service.BrandTypeService;
import org.asdtiang.carbrand.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.asdtiang.carbrand.domain.BrandType}.
 */
@RestController
@RequestMapping("/api")
public class BrandTypeResource {

    private final Logger log = LoggerFactory.getLogger(BrandTypeResource.class);

    private static final String ENTITY_NAME = "brandType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BrandTypeService brandTypeService;

    public BrandTypeResource(BrandTypeService brandTypeService) {
        this.brandTypeService = brandTypeService;
    }

    /**
     * {@code POST  /brand-types} : Create a new brandType.
     *
     * @param brandType the brandType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new brandType, or with status {@code 400 (Bad Request)} if the brandType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/brand-types")
    public ResponseEntity<BrandType> createBrandType(@Valid @RequestBody BrandType brandType) throws URISyntaxException {
        log.debug("REST request to save BrandType : {}", brandType);
        if (brandType.getId() != null) {
            throw new BadRequestAlertException("A new brandType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrandType result = brandTypeService.save(brandType);
        return ResponseEntity.created(new URI("/api/brand-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /brand-types} : Updates an existing brandType.
     *
     * @param brandType the brandType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brandType,
     * or with status {@code 400 (Bad Request)} if the brandType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the brandType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/brand-types")
    public ResponseEntity<BrandType> updateBrandType(@Valid @RequestBody BrandType brandType) throws URISyntaxException {
        log.debug("REST request to update BrandType : {}", brandType);
        if (brandType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BrandType result = brandTypeService.save(brandType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, brandType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /brand-types} : get all the brandTypes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of brandTypes in body.
     */
    @GetMapping("/brand-types")
    public ResponseEntity<List<BrandType>> getAllBrandTypes(Pageable pageable) {
        log.debug("REST request to get a page of BrandTypes");
        Page<BrandType> page = brandTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /brand-types/:id} : get the "id" brandType.
     *
     * @param id the id of the brandType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the brandType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/brand-types/{id}")
    public ResponseEntity<BrandType> getBrandType(@PathVariable Long id) {
        log.debug("REST request to get BrandType : {}", id);
        Optional<BrandType> brandType = brandTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(brandType);
    }

    /**
     * {@code DELETE  /brand-types/:id} : delete the "id" brandType.
     *
     * @param id the id of the brandType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/brand-types/{id}")
    public ResponseEntity<Void> deleteBrandType(@PathVariable Long id) {
        log.debug("REST request to delete BrandType : {}", id);
        brandTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
