package com.clusterpi.entities;


import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 64)
    private String name;
    @Column(length = 64)
    private String description;
    //private Category parentCategory;

    public long getId(){
        return this.id ;
    }

    public String getName(){
        return this.name ;
    }

    public String getDescription(){
        return this.description ;
    }

    public void setId(long id){
        this.id = id ;
    }

    public void setName(String name){
        this.name =name ;
    }

    public void setDescription(String description){
        this.description = description ;
    }

}
