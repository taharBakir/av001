package com.av001.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.av001.domain.InvoiceAddress;
import com.av001.repository.InvoiceAddressRepository;
import com.av001.repository.search.InvoiceAddressSearchRepository;
import com.av001.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing InvoiceAddress.
 */
@RestController
@RequestMapping("/api")
public class InvoiceAddressResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceAddressResource.class);

    @Inject
    private InvoiceAddressRepository invoiceAddressRepository;

    @Inject
    private InvoiceAddressSearchRepository invoiceAddressSearchRepository;

    /**
     * POST  /invoiceAddresss -> Create a new invoiceAddress.
     */
    @RequestMapping(value = "/invoiceAddresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceAddress> createInvoiceAddress(@RequestBody InvoiceAddress invoiceAddress) throws URISyntaxException {
        log.debug("REST request to save InvoiceAddress : {}", invoiceAddress);
        if (invoiceAddress.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new invoiceAddress cannot already have an ID").body(null);
        }
        InvoiceAddress result = invoiceAddressRepository.save(invoiceAddress);
        invoiceAddressSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/invoiceAddresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("invoiceAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoiceAddresss -> Updates an existing invoiceAddress.
     */
    @RequestMapping(value = "/invoiceAddresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceAddress> updateInvoiceAddress(@RequestBody InvoiceAddress invoiceAddress) throws URISyntaxException {
        log.debug("REST request to update InvoiceAddress : {}", invoiceAddress);
        if (invoiceAddress.getId() == null) {
            return createInvoiceAddress(invoiceAddress);
        }
        InvoiceAddress result = invoiceAddressRepository.save(invoiceAddress);
        invoiceAddressSearchRepository.save(invoiceAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("invoiceAddress", invoiceAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoiceAddresss -> get all the invoiceAddresss.
     */
    @RequestMapping(value = "/invoiceAddresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InvoiceAddress> getAllInvoiceAddresss(@RequestParam(required = false) String filter) {
        if ("commande-is-null".equals(filter)) {
            log.debug("REST request to get all InvoiceAddresss where commande is null");
            return StreamSupport
                .stream(invoiceAddressRepository.findAll().spliterator(), false)
                .filter(invoiceAddress -> invoiceAddress.getCommande() == null)
                .collect(Collectors.toList());
        }

        log.debug("REST request to get all InvoiceAddresss");
        return invoiceAddressRepository.findAll();
    }

    /**
     * GET  /invoiceAddresss/:id -> get the "id" invoiceAddress.
     */
    @RequestMapping(value = "/invoiceAddresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceAddress> getInvoiceAddress(@PathVariable Long id) {
        log.debug("REST request to get InvoiceAddress : {}", id);
        return Optional.ofNullable(invoiceAddressRepository.findOne(id))
            .map(invoiceAddress -> new ResponseEntity<>(
                invoiceAddress,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invoiceAddresss/:id -> delete the "id" invoiceAddress.
     */
    @RequestMapping(value = "/invoiceAddresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInvoiceAddress(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceAddress : {}", id);
        invoiceAddressRepository.delete(id);
        invoiceAddressSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("invoiceAddress", id.toString())).build();
    }

    /**
     * SEARCH  /_search/invoiceAddresss/:query -> search for the invoiceAddress corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/invoiceAddresss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InvoiceAddress> searchInvoiceAddresss(@PathVariable String query) {
        return StreamSupport
            .stream(invoiceAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
