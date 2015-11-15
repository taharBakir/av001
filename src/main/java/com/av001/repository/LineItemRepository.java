package com.av001.repository;

import com.av001.domain.Lineitem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LineItem entity.
 */
public interface LineItemRepository extends JpaRepository<Lineitem,Long> {

}
