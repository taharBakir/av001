package com.av001.repository.search;

import com.av001.domain.Commande;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Commande entity.
 */
public interface CommandeSearchRepository extends ElasticsearchRepository<Commande, Long> {
}
