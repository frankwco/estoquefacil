package br.com.estoquefacil.web.rest;

import br.com.estoquefacil.domain.Igreja;
import br.com.estoquefacil.repository.IgrejaRepository;
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
 * REST controller for managing {@link br.com.estoquefacil.domain.Igreja}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IgrejaResource {

    private final Logger log = LoggerFactory.getLogger(IgrejaResource.class);

    private static final String ENTITY_NAME = "igreja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IgrejaRepository igrejaRepository;

    public IgrejaResource(IgrejaRepository igrejaRepository) {
        this.igrejaRepository = igrejaRepository;
    }

    /**
     * {@code POST  /igrejas} : Create a new igreja.
     *
     * @param igreja the igreja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new igreja, or with status {@code 400 (Bad Request)} if the igreja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/igrejas")
    public ResponseEntity<Igreja> createIgreja(@Valid @RequestBody Igreja igreja) throws URISyntaxException {
        log.debug("REST request to save Igreja : {}", igreja);
        if (igreja.getId() != null) {
            throw new BadRequestAlertException("A new igreja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Igreja result = igrejaRepository.save(igreja);
        return ResponseEntity.created(new URI("/api/igrejas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /igrejas} : Updates an existing igreja.
     *
     * @param igreja the igreja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated igreja,
     * or with status {@code 400 (Bad Request)} if the igreja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the igreja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/igrejas")
    public ResponseEntity<Igreja> updateIgreja(@Valid @RequestBody Igreja igreja) throws URISyntaxException {
        log.debug("REST request to update Igreja : {}", igreja);
        if (igreja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Igreja result = igrejaRepository.save(igreja);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, igreja.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /igrejas} : get all the igrejas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of igrejas in body.
     */
    @GetMapping("/igrejas")
    public ResponseEntity<List<Igreja>> getAllIgrejas(Pageable pageable) {
        log.debug("REST request to get a page of Igrejas");
        Page<Igreja> page = igrejaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /igrejas/:id} : get the "id" igreja.
     *
     * @param id the id of the igreja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the igreja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/igrejas/{id}")
    public ResponseEntity<Igreja> getIgreja(@PathVariable Long id) {
        log.debug("REST request to get Igreja : {}", id);
        Optional<Igreja> igreja = igrejaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(igreja);
    }

    /**
     * {@code DELETE  /igrejas/:id} : delete the "id" igreja.
     *
     * @param id the id of the igreja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/igrejas/{id}")
    public ResponseEntity<Void> deleteIgreja(@PathVariable Long id) {
        log.debug("REST request to delete Igreja : {}", id);
        igrejaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
