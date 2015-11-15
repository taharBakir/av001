package com.av001.repository;

import com.av001.domain.InvoiceAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InvoiceAddress entity.
 */
public interface InvoiceAddressRepository extends JpaRepository<InvoiceAddress,Long> {

}
