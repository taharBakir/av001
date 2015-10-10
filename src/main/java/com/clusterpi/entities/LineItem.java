package com.clusterpi.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class LineItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal price;
    private int quantity;

    public long getId() {
        return this.id;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setId(long id) {
        this.id = id ;
    }

    public void setPrice(BigDecimal price) {
        this.price = price ;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

