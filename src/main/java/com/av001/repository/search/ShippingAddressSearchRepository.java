package com.av001.repository.search;

import com.av001.domain.ShippingAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ShippingAddress entity.
 */
public interface ShippingAddressSearchRepository extends ElasticsearchRepository<ShippingAddress, Long> {
}
