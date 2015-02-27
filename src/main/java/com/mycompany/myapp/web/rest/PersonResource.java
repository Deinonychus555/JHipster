package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Person;
import com.mycompany.myapp.repository.PersonRepository;
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
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Inject
    private PersonRepository personRepository;

    /**
     * POST  /people -> Create a new person.
     */
    @RequestMapping(value = "/people",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Person person) {
        log.debug("REST request to save Person : {}", person);
        personRepository.save(person);
    }

    /**
     * GET  /people -> get all the people.
     */
    @RequestMapping(value = "/people",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Person> getAll() {
        log.debug("REST request to get all Persons");
        return personRepository.findAll();
    }

    /**
     * GET  /people/:id -> get the "id" person.
     */
    @RequestMapping(value = "/people/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> get(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        return Optional.ofNullable(personRepository.findOneWithEagerRelationships(id))
            .map(person -> new ResponseEntity<>(
                person,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    /**
     * GET  /people/:breed -> 
     */
    @RequestMapping(value = "/people/pet_breed/{breed}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Person> getPeopleByPetBreed(@PathVariable String breed) {
        log.debug("REST request to get People : {}", breed);
        return personRepository.findByPetsBreed(breed);
            
    }

    /**
     * DELETE  /people/:id -> delete the "id" person.
     */
    @RequestMapping(value = "/people/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.delete(id);
    }
}
