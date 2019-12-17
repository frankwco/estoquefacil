package br.com.estoquefacil.web.rest;

import br.com.estoquefacil.domain.ItensPedido;
import br.com.estoquefacil.repository.ItensPedidoRepository;
import br.com.estoquefacil.web.rest.errors.BadRequestAlertException;

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
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.estoquefacil.domain.ItensPedido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ItensPedidoResource {

    private final Logger log = LoggerFactory.getLogger(ItensPedidoResource.class);

    private static final String ENTITY_NAME = "itensPedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItensPedidoRepository itensPedidoRepository;

    public ItensPedidoResource(ItensPedidoRepository itensPedidoRepository) {
        this.itensPedidoRepository = itensPedidoRepository;
    }

    /**
     * {@code POST  /itens-pedidos} : Create a new itensPedido.
     *
     * @param itensPedido the itensPedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itensPedido, or with status {@code 400 (Bad Request)} if the itensPedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/itens-pedidos")
    public ResponseEntity<ItensPedido> createItensPedido(@Valid @RequestBody ItensPedido itensPedido) throws URISyntaxException {
        log.debug("REST request to save ItensPedido : {}", itensPedido);
        if (itensPedido.getId() != null) {
            throw new BadRequestAlertException("A new itensPedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItensPedido result = itensPedidoRepository.save(itensPedido);
        return ResponseEntity.created(new URI("/api/itens-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /itens-pedidos} : Updates an existing itensPedido.
     *
     * @param itensPedido the itensPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itensPedido,
     * or with status {@code 400 (Bad Request)} if the itensPedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itensPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/itens-pedidos")
    public ResponseEntity<ItensPedido> updateItensPedido(@Valid @RequestBody ItensPedido itensPedido) throws URISyntaxException {
        log.debug("REST request to update ItensPedido : {}", itensPedido);
        if (itensPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItensPedido result = itensPedidoRepository.save(itensPedido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itensPedido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /itens-pedidos} : get all the itensPedidos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itensPedidos in body.
     */
    @GetMapping("/itens-pedidos")
    public ResponseEntity<List<ItensPedido>> getAllItensPedidos(Pageable pageable) {
        log.debug("REST request to get a page of ItensPedidos");
        Page<ItensPedido> page = itensPedidoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /itens-pedidos/:id} : get the "id" itensPedido.
     *
     * @param id the id of the itensPedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itensPedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/itens-pedidos/{id}")
    public ResponseEntity<ItensPedido> getItensPedido(@PathVariable Long id) {
        log.debug("REST request to get ItensPedido : {}", id);
        Optional<ItensPedido> itensPedido = itensPedidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itensPedido);
    }

    /**
     * {@code DELETE  /itens-pedidos/:id} : delete the "id" itensPedido.
     *
     * @param id the id of the itensPedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/itens-pedidos/{id}")
    public ResponseEntity<Void> deleteItensPedido(@PathVariable Long id) {
        log.debug("REST request to delete ItensPedido : {}", id);
        itensPedidoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
