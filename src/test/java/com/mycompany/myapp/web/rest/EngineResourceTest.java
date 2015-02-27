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
import com.mycompany.myapp.domain.Engine;
import com.mycompany.myapp.repository.EngineRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EngineResource REST controller.
 *
 * @see EngineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EngineResourceTest {

    private static final String DEFAULT_MODEL = "SAMPLE_TEXT";
    private static final String UPDATED_MODEL = "UPDATED_TEXT";
    private static final String DEFAULT_POWER = "SAMPLE_TEXT";
    private static final String UPDATED_POWER = "UPDATED_TEXT";

    @Inject
    private EngineRepository engineRepository;

    private MockMvc restEngineMockMvc;

    private Engine engine;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EngineResource engineResource = new EngineResource();
        ReflectionTestUtils.setField(engineResource, "engineRepository", engineRepository);
        this.restEngineMockMvc = MockMvcBuilders.standaloneSetup(engineResource).build();
    }

    @Before
    public void initTest() {
        engine = new Engine();
        engine.setModel(DEFAULT_MODEL);
        engine.setPower(DEFAULT_POWER);
    }

    @Test
    @Transactional
    public void createEngine() throws Exception {
        // Validate the database is empty
        assertThat(engineRepository.findAll()).hasSize(0);

        // Create the Engine
        restEngineMockMvc.perform(post("/api/engines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(engine)))
                .andExpect(status().isOk());

        // Validate the Engine in the database
        List<Engine> engines = engineRepository.findAll();
        assertThat(engines).hasSize(1);
        Engine testEngine = engines.iterator().next();
        assertThat(testEngine.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testEngine.getPower()).isEqualTo(DEFAULT_POWER);
    }

    @Test
    @Transactional
    public void getAllEngines() throws Exception {
        // Initialize the database
        engineRepository.saveAndFlush(engine);

        // Get all the engines
        restEngineMockMvc.perform(get("/api/engines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(engine.getId().intValue()))
                .andExpect(jsonPath("$.[0].model").value(DEFAULT_MODEL.toString()))
                .andExpect(jsonPath("$.[0].power").value(DEFAULT_POWER.toString()));
    }

    @Test
    @Transactional
    public void getEngine() throws Exception {
        // Initialize the database
        engineRepository.saveAndFlush(engine);

        // Get the engine
        restEngineMockMvc.perform(get("/api/engines/{id}", engine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(engine.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.power").value(DEFAULT_POWER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEngine() throws Exception {
        // Get the engine
        restEngineMockMvc.perform(get("/api/engines/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEngine() throws Exception {
        // Initialize the database
        engineRepository.saveAndFlush(engine);

        // Update the engine
        engine.setModel(UPDATED_MODEL);
        engine.setPower(UPDATED_POWER);
        restEngineMockMvc.perform(post("/api/engines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(engine)))
                .andExpect(status().isOk());

        // Validate the Engine in the database
        List<Engine> engines = engineRepository.findAll();
        assertThat(engines).hasSize(1);
        Engine testEngine = engines.iterator().next();
        assertThat(testEngine.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testEngine.getPower()).isEqualTo(UPDATED_POWER);
    }

    @Test
    @Transactional
    public void deleteEngine() throws Exception {
        // Initialize the database
        engineRepository.saveAndFlush(engine);

        // Get the engine
        restEngineMockMvc.perform(delete("/api/engines/{id}", engine.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Engine> engines = engineRepository.findAll();
        assertThat(engines).hasSize(0);
    }
}
