package com.clusterpi.web;

import com.clusterpi.model.Category;
import com.clusterpi.model.Product;
import com.clusterpi.util.EnumCurrency;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping(value = "/GetProduct", method = RequestMethod.GET)
    public Product getProduct(@RequestParam(value = "idProduct", defaultValue = "1") Long idProduct) {

        Category topCaterory = new Category.Builder(1)
                .withName("Top caterory")
                .withDescription("description Top caterory")
                .withParentCategory(null)
                .build();

        Category bottomCaterory = new Category.Builder(2)
                .withName("Bottom caterory")
                .withDescription("description Bottom caterory")
                .withParentCategory(topCaterory)
                .build();

        return new Product.Builder(idProduct)
                .withName("Nom Produit")
                .withDescription("La description du produit")
                .isAvailable(true)
                .isOnline(true)
                .withPrice(15.90)
                .withCurrency(EnumCurrency.EUR)
                .withCategory(bottomCaterory)
                .build();


    /*

    {
        "id":2,
        "name":"Nom Produit",
        "description":"La description du produit",
        "available":true,
        "online":true,
        "price":15.9,
        "currency":"EUR",
        "category":{
            "id":2,
            "name":"Bottom caterory",
            "description":"description Bottom caterory",
            "parentCategory":{
                "id":1,
                "name":"Top caterory",
                "description":"description Top caterory",
                "parentCategory":null
                }
        }
    }

    */

    }


}


