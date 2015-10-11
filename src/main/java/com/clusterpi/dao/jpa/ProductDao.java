package com.clusterpi.dao.jpa;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

import com.clusterpi.entities.Product;

@Repository
public class ProductDao {


    @PersistenceContext
    private EntityManager entityManager;

    public Product findProductById(long id) {
        Product c = null;
        try {
            return entityManager.createNamedQuery("findProductById", Product.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException nre) {
        }
        return c;
    }
}
