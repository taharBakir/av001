package com.clusterpi.entities;


import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Instant date;
    //private Address shippingAddress;
    //private Address invoiceAddress;
    //private User customer;

    public long getId() {
        return this.id;
    }

    public Instant getDate() {
        return this.date;
    }

    public void setId(long id) {
        this.id = id ;
    }

    public void setDate(Instant date) {
        this.date = date ;
    }

}
