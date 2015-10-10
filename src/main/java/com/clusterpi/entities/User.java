package com.clusterpi.entities;


import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.*;

@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 64)
    private String name;
    //private HashMap<Long, Address> addresses;

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
