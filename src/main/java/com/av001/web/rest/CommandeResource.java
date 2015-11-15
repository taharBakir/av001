package com.av001.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.av001.domain.Commande;
import com.av001.repository.CommandeRepository;
import com.av001.repository.search.CommandeSearchRepository;
import com.av001.web.rest.util.HeaderUtil;
import com.av001.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Commande.
 */
@RestController
@RequestMapping("/api")
public class CommandeResource {

    private final Logger log = LoggerFactory.getLogger(CommandeResource.class);

    @Inject
    private CommandeRepository commandeRepository;

    @Inject
    private CommandeSearchRepository commandeSearchRepository;

    /**
     * POST  /commandes -> Create a new commande.
     */
    @RequestMapping(value = "/commandes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) throws URISyntaxException {
        log.debug("REST request to save Commande : {}", commande);
        if (commande.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new commande cannot already have an ID").body(null);
        }
        Commande result = commandeRepository.save(commande);
        commandeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("commande", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commandes -> Updates an existing commande.
     */
    @RequestMapping(value = "/commandes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Commande> updateCommande(@RequestBody Commande commande) throws URISyntaxException {
        log.debug("REST request to update Commande : {}", commande);
        if (commande.getId() == null) {
            return createCommande(commande);
        }
        Commande result = commandeRepository.save(commande);
        commandeSearchRepository.save(commande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("commande", commande.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commandes -> get all the commandes.
     */
    @RequestMapping(value = "/commandes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Commande>> getAllCommandes(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("shippingaddress-is-null".equals(filter)) {
            log.debug("REST request to get all Commandes where shippingAddress is null");
            return new ResponseEntity<>(StreamSupport
                .stream(commandeRepository.findAll().spliterator(), false)
                .filter(commande -> commande.getShippingAddress() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        
        if ("invoiceaddress-is-null".equals(filter)) {
            log.debug("REST request to get all Commandes where invoiceAddress is null");
            return new ResponseEntity<>(StreamSupport
                .stream(commandeRepository.findAll().spliterator(), false)
                .filter(commande -> commande.getInvoiceAddress() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        
        Page<Commande> page = commandeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commandes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commandes/:id -> get the "id" commande.
     */
    @RequestMapping(value = "/commandes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Commande> getCommande(@PathVariable Long id) {
        log.debug("REST request to get Commande : {}", id);
        return Optional.ofNullable(commandeRepository.findOne(id))
            .map(commande -> new ResponseEntity<>(
                commande,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /commandes/:id -> delete the "id" commande.
     */
    @RequestMapping(value = "/commandes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        log.debug("REST request to delete Commande : {}", id);
        commandeRepository.delete(id);
        commandeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("commande", id.toString())).build();
    }

    /**
     * SEARCH  /_search/commandes/:query -> search for the commande corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/commandes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Commande> searchCommandes(@PathVariable String query) {
        return StreamSupport
            .stream(commandeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
