package com.av001.repository;

import com.av001.domain.ShippingAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShippingAddress entity.
 */
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long> {

}
