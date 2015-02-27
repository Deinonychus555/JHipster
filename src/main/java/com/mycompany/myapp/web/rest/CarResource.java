package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Car;
import com.mycompany.myapp.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Car.
 */
@RestController
@RequestMapping("/api")
public class CarResource {

    private final Logger log = LoggerFactory.getLogger(CarResource.class);

    @Inject
    private CarRepository carRepository;

    /**
     * POST  /cars -> Create a new car.
     */
    @RequestMapping(value = "/cars",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Car car) {
        log.debug("REST request to save Car : {}", car);
        carRepository.save(car);
    }

    /**
     * GET  /cars -> get all the cars.
     */
    @RequestMapping(value = "/cars",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Car> getAll() {
        log.debug("REST request to get all Cars");
        return carRepository.findAll();
    }

    /**
     * GET  /cars/:id -> get the "id" car.
     */
    @RequestMapping(value = "/cars/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Car> get(@PathVariable Long id) {
        log.debug("REST request to get Car : {}", id);
        return Optional.ofNullable(carRepository.findOne(id))
            .map(car -> new ResponseEntity<>(
                car,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cars/:id -> delete the "id" car.
     */
    @RequestMapping(value = "/cars/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Car : {}", id);
        carRepository.delete(id);
    }
}
