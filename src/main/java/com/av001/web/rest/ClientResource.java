package com.av001.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.av001.domain.Client;
import com.av001.repository.ClientRepository;
import com.av001.repository.search.ClientSearchRepository;
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
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private ClientSearchRepository clientSearchRepository;

    /**
     * POST  /clients -> Create a new client.
     */
    @RequestMapping(value = "/clients",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to save Client : {}", client);
        if (client.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new client cannot already have an ID").body(null);
        }
        Client result = clientRepository.save(client);
        clientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("client", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clients -> Updates an existing client.
     */
    @RequestMapping(value = "/clients",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client> updateClient(@RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to update Client : {}", client);
        if (client.getId() == null) {
            return createClient(client);
        }
        Client result = clientRepository.save(client);
        clientSearchRepository.save(client);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("client", client.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clients -> get all the clients.
     */
    @RequestMapping(value = "/clients",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Client> getAllClients() {
        log.debug("REST request to get all Clients");
        return clientRepository.findAll();
    }

    /**
     * GET  /clients/:id -> get the "id" client.
     */
    @RequestMapping(value = "/clients/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        return Optional.ofNullable(clientRepository.findOne(id))
            .map(client -> new ResponseEntity<>(
                client,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clients/:id -> delete the "id" client.
     */
    @RequestMapping(value = "/clients/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientRepository.delete(id);
        clientSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("client", id.toString())).build();
    }

    /**
     * SEARCH  /_search/clients/:query -> search for the client corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/clients/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Client> searchClients(@PathVariable String query) {
        return StreamSupport
            .stream(clientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
