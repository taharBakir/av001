package com.av001.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.av001.domain.ShippingAddress;
import com.av001.repository.ShippingAddressRepository;
import com.av001.repository.search.ShippingAddressSearchRepository;
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
 * REST controller for managing ShippingAddress.
 */
@RestController
@RequestMapping("/api")
public class ShippingAddressResource {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressResource.class);

    @Inject
    private ShippingAddressRepository shippingAddressRepository;

    @Inject
    private ShippingAddressSearchRepository shippingAddressSearchRepository;

    /**
     * POST  /shippingAddresss -> Create a new shippingAddress.
     */
    @RequestMapping(value = "/shippingAddresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShippingAddress> createShippingAddress(@RequestBody ShippingAddress shippingAddress) throws URISyntaxException {
        log.debug("REST request to save ShippingAddress : {}", shippingAddress);
        if (shippingAddress.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shippingAddress cannot already have an ID").body(null);
        }
        ShippingAddress result = shippingAddressRepository.save(shippingAddress);
        shippingAddressSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/shippingAddresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("shippingAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shippingAddresss -> Updates an existing shippingAddress.
     */
    @RequestMapping(value = "/shippingAddresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShippingAddress> updateShippingAddress(@RequestBody ShippingAddress shippingAddress) throws URISyntaxException {
        log.debug("REST request to update ShippingAddress : {}", shippingAddress);
        if (shippingAddress.getId() == null) {
            return createShippingAddress(shippingAddress);
        }
        ShippingAddress result = shippingAddressRepository.save(shippingAddress);
        shippingAddressSearchRepository.save(shippingAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("shippingAddress", shippingAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shippingAddresss -> get all the shippingAddresss.
     */
    @RequestMapping(value = "/shippingAddresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ShippingAddress> getAllShippingAddresss(@RequestParam(required = false) String filter) {
        if ("commande-is-null".equals(filter)) {
            log.debug("REST request to get all ShippingAddresss where commande is null");
            return StreamSupport
                .stream(shippingAddressRepository.findAll().spliterator(), false)
                .filter(shippingAddress -> shippingAddress.getCommande() == null)
                .collect(Collectors.toList());
        }

        log.debug("REST request to get all ShippingAddresss");
        return shippingAddressRepository.findAll();
    }

    /**
     * GET  /shippingAddresss/:id -> get the "id" shippingAddress.
     */
    @RequestMapping(value = "/shippingAddresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShippingAddress> getShippingAddress(@PathVariable Long id) {
        log.debug("REST request to get ShippingAddress : {}", id);
        return Optional.ofNullable(shippingAddressRepository.findOne(id))
            .map(shippingAddress -> new ResponseEntity<>(
                shippingAddress,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shippingAddresss/:id -> delete the "id" shippingAddress.
     */
    @RequestMapping(value = "/shippingAddresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable Long id) {
        log.debug("REST request to delete ShippingAddress : {}", id);
        shippingAddressRepository.delete(id);
        shippingAddressSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("shippingAddress", id.toString())).build();
    }

    /**
     * SEARCH  /_search/shippingAddresss/:query -> search for the shippingAddress corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/shippingAddresss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ShippingAddress> searchShippingAddresss(@PathVariable String query) {
        return StreamSupport
            .stream(shippingAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
