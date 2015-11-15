package com.av001.repository;

import com.av001.domain.Commande;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Commande entity.
 */
public interface CommandeRepository extends JpaRepository<Commande,Long> {

}
