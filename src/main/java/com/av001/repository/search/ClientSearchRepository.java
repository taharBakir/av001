package com.av001.repository.search;

import com.av001.domain.Client;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Client entity.
 */
public interface ClientSearchRepository extends ElasticsearchRepository<Client, Long> {
}
