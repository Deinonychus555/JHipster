package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Animal;
import com.mycompany.myapp.repository.AnimalRepository;
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
 * REST controller for managing Animal.
 */
@RestController
@RequestMapping("/api")
public class AnimalResource {

    private final Logger log = LoggerFactory.getLogger(AnimalResource.class);

    @Inject
    private AnimalRepository animalRepository;

    /**
     * POST  /animals -> Create a new animal.
     */
    @RequestMapping(value = "/animals",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Animal animal) {
        log.debug("REST request to save Animal : {}", animal);
        animalRepository.save(animal);
    }

    /**
     * GET  /animals -> get all the animals.
     */
    @RequestMapping(value = "/animals",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Animal> getAll() {
        log.debug("REST request to get all Animals");
        return animalRepository.findAll();
    }

    /**
     * GET  /animals/:id -> get the "id" animal.
     */
    @RequestMapping(value = "/animals/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Animal> get(@PathVariable Long id) {
        log.debug("REST request to get Animal : {}", id);
        return Optional.ofNullable(animalRepository.findOne(id))
            .map(animal -> new ResponseEntity<>(
                animal,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /animals/:id -> delete the "id" animal.
     */
    @RequestMapping(value = "/animals/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Animal : {}", id);
        animalRepository.delete(id);
    }
}
