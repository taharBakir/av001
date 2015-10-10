package com.clusterpi.entities;

import com.clusterpi.util.EnumCurrency;

import java.io.Serializable;

import javax.persistence.*;


@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 64)
    private String name;
    @Column(length = 64)
    private String description;
    private boolean available;
    private boolean online;
    private double price;
    //private EnumCurrency currency;
    //private Category category;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id ;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description ;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isOnline() {
        return this.online;
    }

    public void setOnline(boolean online) {
        this.online = online ;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}

