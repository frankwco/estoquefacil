package br.com.estoquefacil.web.rest;

import br.com.estoquefacil.EstoquefacilApp;
import br.com.estoquefacil.domain.Igreja;
import br.com.estoquefacil.repository.IgrejaRepository;
import br.com.estoquefacil.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.estoquefacil.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IgrejaResource} REST controller.
 */
@SpringBootTest(classes = EstoquefacilApp.class)
public class IgrejaResourceIT {

    private static final String DEFAULT_NOME_LOCALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_LOCALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_LOCALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_LOCALIDADE = "BBBBBBBBBB";

    @Autowired
    private IgrejaRepository igrejaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIgrejaMockMvc;

    private Igreja igreja;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IgrejaResource igrejaResource = new IgrejaResource(igrejaRepository);
        this.restIgrejaMockMvc = MockMvcBuilders.standaloneSetup(igrejaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Igreja createEntity(EntityManager em) {
        Igreja igreja = new Igreja()
            .nomeLocalidade(DEFAULT_NOME_LOCALIDADE)
            .numeroLocalidade(DEFAULT_NUMERO_LOCALIDADE);
        return igreja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Igreja createUpdatedEntity(EntityManager em) {
        Igreja igreja = new Igreja()
            .nomeLocalidade(UPDATED_NOME_LOCALIDADE)
            .numeroLocalidade(UPDATED_NUMERO_LOCALIDADE);
        return igreja;
    }

    @BeforeEach
    public void initTest() {
        igreja = createEntity(em);
    }

    @Test
    @Transactional
    public void createIgreja() throws Exception {
        int databaseSizeBeforeCreate = igrejaRepository.findAll().size();

        // Create the Igreja
        restIgrejaMockMvc.perform(post("/api/igrejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(igreja)))
            .andExpect(status().isCreated());

        // Validate the Igreja in the database
        List<Igreja> igrejaList = igrejaRepository.findAll();
        assertThat(igrejaList).hasSize(databaseSizeBeforeCreate + 1);
        Igreja testIgreja = igrejaList.get(igrejaList.size() - 1);
        assertThat(testIgreja.getNomeLocalidade()).isEqualTo(DEFAULT_NOME_LOCALIDADE);
        assertThat(testIgreja.getNumeroLocalidade()).isEqualTo(DEFAULT_NUMERO_LOCALIDADE);
    }

    @Test
    @Transactional
    public void createIgrejaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = igrejaRepository.findAll().size();

        // Create the Igreja with an existing ID
        igreja.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIgrejaMockMvc.perform(post("/api/igrejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(igreja)))
            .andExpect(status().isBadRequest());

        // Validate the Igreja in the database
        List<Igreja> igrejaList = igrejaRepository.findAll();
        assertThat(igrejaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeLocalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = igrejaRepository.findAll().size();
        // set the field null
        igreja.setNomeLocalidade(null);

        // Create the Igreja, which fails.

        restIgrejaMockMvc.perform(post("/api/igrejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(igreja)))
            .andExpect(status().isBadRequest());

        List<Igreja> igrejaList = igrejaRepository.findAll();
        assertThat(igrejaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroLocalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = igrejaRepository.findAll().size();
        // set the field null
        igreja.setNumeroLocalidade(null);

        // Create the Igreja, which fails.

        restIgrejaMockMvc.perform(post("/api/igrejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(igreja)))
            .andExpect(status().isBadRequest());

        List<Igreja> igrejaList = igrejaRepository.findAll();
        assertThat(igrejaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIgrejas() throws Exception {
        // Initialize the database
        igrejaRepository.saveAndFlush(igreja);

        // Get all the igrejaList
        restIgrejaMockMvc.perform(get("/api/igrejas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(igreja.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeLocalidade").value(hasItem(DEFAULT_NOME_LOCALIDADE)))
            .andExpect(jsonPath("$.[*].numeroLocalidade").value(hasItem(DEFAULT_NUMERO_LOCALIDADE)));
    }
    
    @Test
    @Transactional
    public void getIgreja() throws Exception {
        // Initialize the database
        igrejaRepository.saveAndFlush(igreja);

        // Get the igreja
        restIgrejaMockMvc.perform(get("/api/igrejas/{id}", igreja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(igreja.getId().intValue()))
            .andExpect(jsonPath("$.nomeLocalidade").value(DEFAULT_NOME_LOCALIDADE))
            .andExpect(jsonPath("$.numeroLocalidade").value(DEFAULT_NUMERO_LOCALIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingIgreja() throws Exception {
        // Get the igreja
        restIgrejaMockMvc.perform(get("/api/igrejas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIgreja() throws Exception {
        // Initialize the database
        igrejaRepository.saveAndFlush(igreja);

        int databaseSizeBeforeUpdate = igrejaRepository.findAll().size();

        // Update the igreja
        Igreja updatedIgreja = igrejaRepository.findById(igreja.getId()).get();
        // Disconnect from session so that the updates on updatedIgreja are not directly saved in db
        em.detach(updatedIgreja);
        updatedIgreja
            .nomeLocalidade(UPDATED_NOME_LOCALIDADE)
            .numeroLocalidade(UPDATED_NUMERO_LOCALIDADE);

        restIgrejaMockMvc.perform(put("/api/igrejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIgreja)))
            .andExpect(status().isOk());

        // Validate the Igreja in the database
        List<Igreja> igrejaList = igrejaRepository.findAll();
        assertThat(igrejaList).hasSize(databaseSizeBeforeUpdate);
        Igreja testIgreja = igrejaList.get(igrejaList.size() - 1);
        assertThat(testIgreja.getNomeLocalidade()).isEqualTo(UPDATED_NOME_LOCALIDADE);
        assertThat(testIgreja.getNumeroLocalidade()).isEqualTo(UPDATED_NUMERO_LOCALIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingIgreja() throws Exception {
        int databaseSizeBeforeUpdate = igrejaRepository.findAll().size();

        // Create the Igreja

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIgrejaMockMvc.perform(put("/api/igrejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(igreja)))
            .andExpect(status().isBadRequest());

        // Validate the Igreja in the database
        List<Igreja> igrejaList = igrejaRepository.findAll();
        assertThat(igrejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIgreja() throws Exception {
        // Initialize the database
        igrejaRepository.saveAndFlush(igreja);

        int databaseSizeBeforeDelete = igrejaRepository.findAll().size();

        // Delete the igreja
        restIgrejaMockMvc.perform(delete("/api/igrejas/{id}", igreja.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Igreja> igrejaList = igrejaRepository.findAll();
        assertThat(igrejaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
