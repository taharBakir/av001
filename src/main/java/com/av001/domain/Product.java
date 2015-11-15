package com.av001.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "prix_ht")
    private String prixHT;

    @Column(name = "prix_ttc")
    private String prixTTC;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lineitem> lineitems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(String prixHT) {
        this.prixHT = prixHT;
    }

    public String getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(String prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Set<Lineitem> getLineitems() {
        return lineitems;
    }

    public void setLineitems(Set<Lineitem> lineitems) {
        this.lineitems = lineitems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if ( ! Objects.equals(id, product.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", prixHT='" + prixHT + "'" +
            ", prixTTC='" + prixTTC + "'" +
            '}';
    }
}
