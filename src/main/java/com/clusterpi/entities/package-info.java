@NamedQueries({
        @NamedQuery(name = "findProductById", query = "SELECT p FROM Product WHERE Product.id = :id"),
})
package com.clusterpi.entities;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;