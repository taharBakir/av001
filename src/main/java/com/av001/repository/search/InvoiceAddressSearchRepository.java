package com.av001.repository.search;

import com.av001.domain.InvoiceAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InvoiceAddress entity.
 */
public interface InvoiceAddressSearchRepository extends ElasticsearchRepository<InvoiceAddress, Long> {
}
