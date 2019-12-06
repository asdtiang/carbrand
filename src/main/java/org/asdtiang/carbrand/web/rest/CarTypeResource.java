package org.asdtiang.carbrand.web.rest;

import org.asdtiang.carbrand.domain.CarType;
import org.asdtiang.carbrand.service.CarTypeService;
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
 * REST controller for managing {@link org.asdtiang.carbrand.domain.CarType}.
 */
@RestController
@RequestMapping("/api")
public class CarTypeResource {

    private final Logger log = LoggerFactory.getLogger(CarTypeResource.class);

    private static final String ENTITY_NAME = "carType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarTypeService carTypeService;

    public CarTypeResource(CarTypeService carTypeService) {
        this.carTypeService = carTypeService;
    }

    /**
     * {@code POST  /car-types} : Create a new carType.
     *
     * @param carType the carType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carType, or with status {@code 400 (Bad Request)} if the carType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/car-types")
    public ResponseEntity<CarType> createCarType(@Valid @RequestBody CarType carType) throws URISyntaxException {
        log.debug("REST request to save CarType : {}", carType);
        if (carType.getId() != null) {
            throw new BadRequestAlertException("A new carType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarType result = carTypeService.save(carType);
        return ResponseEntity.created(new URI("/api/car-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /car-types} : Updates an existing carType.
     *
     * @param carType the carType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carType,
     * or with status {@code 400 (Bad Request)} if the carType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/car-types")
    public ResponseEntity<CarType> updateCarType(@Valid @RequestBody CarType carType) throws URISyntaxException {
        log.debug("REST request to update CarType : {}", carType);
        if (carType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarType result = carTypeService.save(carType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /car-types} : get all the carTypes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carTypes in body.
     */
    @GetMapping("/car-types")
    public ResponseEntity<List<CarType>> getAllCarTypes(Pageable pageable) {
        log.debug("REST request to get a page of CarTypes");
        Page<CarType> page = carTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /car-types/:id} : get the "id" carType.
     *
     * @param id the id of the carType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/car-types/{id}")
    public ResponseEntity<CarType> getCarType(@PathVariable Long id) {
        log.debug("REST request to get CarType : {}", id);
        Optional<CarType> carType = carTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carType);
    }

    /**
     * {@code DELETE  /car-types/:id} : delete the "id" carType.
     *
     * @param id the id of the carType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/car-types/{id}")
    public ResponseEntity<Void> deleteCarType(@PathVariable Long id) {
        log.debug("REST request to delete CarType : {}", id);
        carTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
