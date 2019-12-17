package br.com.estoquefacil.web.rest;

import br.com.estoquefacil.EstoquefacilApp;
import br.com.estoquefacil.domain.ItensPedido;
import br.com.estoquefacil.repository.ItensPedidoRepository;
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
 * Integration tests for the {@link ItensPedidoResource} REST controller.
 */
@SpringBootTest(classes = EstoquefacilApp.class)
public class ItensPedidoResourceIT {

    private static final Double DEFAULT_QUANTIDADE_PRODUTO = 1D;
    private static final Double UPDATED_QUANTIDADE_PRODUTO = 2D;

    private static final Double DEFAULT_VALOR_PRODUTO = 1D;
    private static final Double UPDATED_VALOR_PRODUTO = 2D;

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

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

    private MockMvc restItensPedidoMockMvc;

    private ItensPedido itensPedido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItensPedidoResource itensPedidoResource = new ItensPedidoResource(itensPedidoRepository);
        this.restItensPedidoMockMvc = MockMvcBuilders.standaloneSetup(itensPedidoResource)
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
    public static ItensPedido createEntity(EntityManager em) {
        ItensPedido itensPedido = new ItensPedido()
            .quantidadeProduto(DEFAULT_QUANTIDADE_PRODUTO)
            .valorProduto(DEFAULT_VALOR_PRODUTO);
        return itensPedido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItensPedido createUpdatedEntity(EntityManager em) {
        ItensPedido itensPedido = new ItensPedido()
            .quantidadeProduto(UPDATED_QUANTIDADE_PRODUTO)
            .valorProduto(UPDATED_VALOR_PRODUTO);
        return itensPedido;
    }

    @BeforeEach
    public void initTest() {
        itensPedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createItensPedido() throws Exception {
        int databaseSizeBeforeCreate = itensPedidoRepository.findAll().size();

        // Create the ItensPedido
        restItensPedidoMockMvc.perform(post("/api/itens-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensPedido)))
            .andExpect(status().isCreated());

        // Validate the ItensPedido in the database
        List<ItensPedido> itensPedidoList = itensPedidoRepository.findAll();
        assertThat(itensPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        ItensPedido testItensPedido = itensPedidoList.get(itensPedidoList.size() - 1);
        assertThat(testItensPedido.getQuantidadeProduto()).isEqualTo(DEFAULT_QUANTIDADE_PRODUTO);
        assertThat(testItensPedido.getValorProduto()).isEqualTo(DEFAULT_VALOR_PRODUTO);
    }

    @Test
    @Transactional
    public void createItensPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itensPedidoRepository.findAll().size();

        // Create the ItensPedido with an existing ID
        itensPedido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItensPedidoMockMvc.perform(post("/api/itens-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensPedido)))
            .andExpect(status().isBadRequest());

        // Validate the ItensPedido in the database
        List<ItensPedido> itensPedidoList = itensPedidoRepository.findAll();
        assertThat(itensPedidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantidadeProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itensPedidoRepository.findAll().size();
        // set the field null
        itensPedido.setQuantidadeProduto(null);

        // Create the ItensPedido, which fails.

        restItensPedidoMockMvc.perform(post("/api/itens-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensPedido)))
            .andExpect(status().isBadRequest());

        List<ItensPedido> itensPedidoList = itensPedidoRepository.findAll();
        assertThat(itensPedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itensPedidoRepository.findAll().size();
        // set the field null
        itensPedido.setValorProduto(null);

        // Create the ItensPedido, which fails.

        restItensPedidoMockMvc.perform(post("/api/itens-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensPedido)))
            .andExpect(status().isBadRequest());

        List<ItensPedido> itensPedidoList = itensPedidoRepository.findAll();
        assertThat(itensPedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItensPedidos() throws Exception {
        // Initialize the database
        itensPedidoRepository.saveAndFlush(itensPedido);

        // Get all the itensPedidoList
        restItensPedidoMockMvc.perform(get("/api/itens-pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itensPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeProduto").value(hasItem(DEFAULT_QUANTIDADE_PRODUTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorProduto").value(hasItem(DEFAULT_VALOR_PRODUTO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getItensPedido() throws Exception {
        // Initialize the database
        itensPedidoRepository.saveAndFlush(itensPedido);

        // Get the itensPedido
        restItensPedidoMockMvc.perform(get("/api/itens-pedidos/{id}", itensPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itensPedido.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeProduto").value(DEFAULT_QUANTIDADE_PRODUTO.doubleValue()))
            .andExpect(jsonPath("$.valorProduto").value(DEFAULT_VALOR_PRODUTO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItensPedido() throws Exception {
        // Get the itensPedido
        restItensPedidoMockMvc.perform(get("/api/itens-pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItensPedido() throws Exception {
        // Initialize the database
        itensPedidoRepository.saveAndFlush(itensPedido);

        int databaseSizeBeforeUpdate = itensPedidoRepository.findAll().size();

        // Update the itensPedido
        ItensPedido updatedItensPedido = itensPedidoRepository.findById(itensPedido.getId()).get();
        // Disconnect from session so that the updates on updatedItensPedido are not directly saved in db
        em.detach(updatedItensPedido);
        updatedItensPedido
            .quantidadeProduto(UPDATED_QUANTIDADE_PRODUTO)
            .valorProduto(UPDATED_VALOR_PRODUTO);

        restItensPedidoMockMvc.perform(put("/api/itens-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItensPedido)))
            .andExpect(status().isOk());

        // Validate the ItensPedido in the database
        List<ItensPedido> itensPedidoList = itensPedidoRepository.findAll();
        assertThat(itensPedidoList).hasSize(databaseSizeBeforeUpdate);
        ItensPedido testItensPedido = itensPedidoList.get(itensPedidoList.size() - 1);
        assertThat(testItensPedido.getQuantidadeProduto()).isEqualTo(UPDATED_QUANTIDADE_PRODUTO);
        assertThat(testItensPedido.getValorProduto()).isEqualTo(UPDATED_VALOR_PRODUTO);
    }

    @Test
    @Transactional
    public void updateNonExistingItensPedido() throws Exception {
        int databaseSizeBeforeUpdate = itensPedidoRepository.findAll().size();

        // Create the ItensPedido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItensPedidoMockMvc.perform(put("/api/itens-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensPedido)))
            .andExpect(status().isBadRequest());

        // Validate the ItensPedido in the database
        List<ItensPedido> itensPedidoList = itensPedidoRepository.findAll();
        assertThat(itensPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItensPedido() throws Exception {
        // Initialize the database
        itensPedidoRepository.saveAndFlush(itensPedido);

        int databaseSizeBeforeDelete = itensPedidoRepository.findAll().size();

        // Delete the itensPedido
        restItensPedidoMockMvc.perform(delete("/api/itens-pedidos/{id}", itensPedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItensPedido> itensPedidoList = itensPedidoRepository.findAll();
        assertThat(itensPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
