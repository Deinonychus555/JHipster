package com.mycompany.myapp.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Animal;
import com.mycompany.myapp.repository.AnimalRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnimalResource REST controller.
 *
 * @see AnimalResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AnimalResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_BREED = "SAMPLE_TEXT";
    private static final String UPDATED_BREED = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;

    @Inject
    private AnimalRepository animalRepository;

    private MockMvc restAnimalMockMvc;

    private Animal animal;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnimalResource animalResource = new AnimalResource();
        ReflectionTestUtils.setField(animalResource, "animalRepository", animalRepository);
        this.restAnimalMockMvc = MockMvcBuilders.standaloneSetup(animalResource).build();
    }

    @Before
    public void initTest() {
        animal = new Animal();
        animal.setName(DEFAULT_NAME);
        animal.setBreed(DEFAULT_BREED);
        animal.setAge(DEFAULT_AGE);
    }

    @Test
    @Transactional
    public void createAnimal() throws Exception {
        // Validate the database is empty
        assertThat(animalRepository.findAll()).hasSize(0);

        // Create the Animal
        restAnimalMockMvc.perform(post("/api/animals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(animal)))
                .andExpect(status().isOk());

        // Validate the Animal in the database
        List<Animal> animals = animalRepository.findAll();
        assertThat(animals).hasSize(1);
        Animal testAnimal = animals.iterator().next();
        assertThat(testAnimal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnimal.getBreed()).isEqualTo(DEFAULT_BREED);
        assertThat(testAnimal.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    public void getAllAnimals() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animals
        restAnimalMockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(animal.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].breed").value(DEFAULT_BREED.toString()))
                .andExpect(jsonPath("$.[0].age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    public void getAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", animal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(animal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.breed").value(DEFAULT_BREED.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    public void getNonExistingAnimal() throws Exception {
        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Update the animal
        animal.setName(UPDATED_NAME);
        animal.setBreed(UPDATED_BREED);
        animal.setAge(UPDATED_AGE);
        restAnimalMockMvc.perform(post("/api/animals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(animal)))
                .andExpect(status().isOk());

        // Validate the Animal in the database
        List<Animal> animals = animalRepository.findAll();
        assertThat(animals).hasSize(1);
        Animal testAnimal = animals.iterator().next();
        assertThat(testAnimal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnimal.getBreed()).isEqualTo(UPDATED_BREED);
        assertThat(testAnimal.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    public void deleteAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get the animal
        restAnimalMockMvc.perform(delete("/api/animals/{id}", animal.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Animal> animals = animalRepository.findAll();
        assertThat(animals).hasSize(0);
    }
}
