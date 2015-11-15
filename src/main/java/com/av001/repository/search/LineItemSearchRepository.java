package com.av001.repository.search;

import com.av001.domain.Lineitem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LineItem entity.
 */
public interface LineItemSearchRepository extends ElasticsearchRepository<Lineitem, Long> {
}
