package com.av001.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.av001.domain.Lineitem;
import com.av001.repository.LineItemRepository;
import com.av001.repository.search.LineItemSearchRepository;
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
 * REST controller for managing LineItem.
 */
@RestController
@RequestMapping("/api")
public class LineItemResource {

    private final Logger log = LoggerFactory.getLogger(LineItemResource.class);

    @Inject
    private LineItemRepository lineItemRepository;

    @Inject
    private LineItemSearchRepository lineItemSearchRepository;

    /**
     * POST  /lineItems -> Create a new lineItem.
     */
    @RequestMapping(value = "/lineItems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lineitem> createLineItem(@RequestBody Lineitem lineItem) throws URISyntaxException {
        log.debug("REST request to save LineItem : {}", lineItem);
        if (lineItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new lineItem cannot already have an ID").body(null);
        }
        Lineitem result = lineItemRepository.save(lineItem);
        lineItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lineItems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lineItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lineItems -> Updates an existing lineItem.
     */
    @RequestMapping(value = "/lineItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lineitem> updateLineItem(@RequestBody Lineitem lineItem) throws URISyntaxException {
        log.debug("REST request to update LineItem : {}", lineItem);
        if (lineItem.getId() == null) {
            return createLineItem(lineItem);
        }
        Lineitem result = lineItemRepository.save(lineItem);
        lineItemSearchRepository.save(lineItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lineItem", lineItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lineItems -> get all the lineItems.
     */
    @RequestMapping(value = "/lineItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lineitem> getAllLineItems(@RequestParam(required = false) String filter) {
        if ("product-is-null".equals(filter)) {
            log.debug("REST request to get all LineItems where product is null");
            return StreamSupport
                .stream(lineItemRepository.findAll().spliterator(), false)
                .filter(lineItem -> lineItem.getProduct() == null)
                .collect(Collectors.toList());
        }

        log.debug("REST request to get all LineItems");
        return lineItemRepository.findAll();
    }

    /**
     * GET  /lineItems/:id -> get the "id" lineItem.
     */
    @RequestMapping(value = "/lineItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lineitem> getLineItem(@PathVariable Long id) {
        log.debug("REST request to get LineItem : {}", id);
        return Optional.ofNullable(lineItemRepository.findOne(id))
            .map(lineItem -> new ResponseEntity<>(
                lineItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lineItems/:id -> delete the "id" lineItem.
     */
    @RequestMapping(value = "/lineItems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLineItem(@PathVariable Long id) {
        log.debug("REST request to delete LineItem : {}", id);
        lineItemRepository.delete(id);
        lineItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lineItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lineItems/:query -> search for the lineItem corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lineItems/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lineitem> searchLineItems(@PathVariable String query) {
        return StreamSupport
            .stream(lineItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
