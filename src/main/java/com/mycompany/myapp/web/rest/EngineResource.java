package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Engine;
import com.mycompany.myapp.repository.EngineRepository;
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
 * REST controller for managing Engine.
 */
@RestController
@RequestMapping("/api")
public class EngineResource {

    private final Logger log = LoggerFactory.getLogger(EngineResource.class);

    @Inject
    private EngineRepository engineRepository;

    /**
     * POST  /engines -> Create a new engine.
     */
    @RequestMapping(value = "/engines",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Engine engine) {
        log.debug("REST request to save Engine : {}", engine);
        engineRepository.save(engine);
    }

    /**
     * GET  /engines -> get all the engines.
     */
    @RequestMapping(value = "/engines",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Engine> getAll() {
        log.debug("REST request to get all Engines");
        return engineRepository.findAll();
    }

    /**
     * GET  /engines/:id -> get the "id" engine.
     */
    @RequestMapping(value = "/engines/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Engine> get(@PathVariable Long id) {
        log.debug("REST request to get Engine : {}", id);
        return Optional.ofNullable(engineRepository.findOne(id))
            .map(engine -> new ResponseEntity<>(
                engine,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /engines/:id -> delete the "id" engine.
     */
    @RequestMapping(value = "/engines/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Engine : {}", id);
        engineRepository.delete(id);
    }
}
