package com.clusterpi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import com.clusterpi.model.Product;
import com.clusterpi.util.EnumCurrency;

@RestController
public class HelloController {

    @RequestMapping(value="/GetProduct",method=RequestMethod.GET) 
    public Product getPomme(@RequestParam(value="idProduct", defaultValue="1") Long idProduct) {
        return new Product.Builder(idProduct)
                          .withName("Nom Produit")
                          .withDescription("La description du produit")
                          .isAvailable(true)
                          .isOnline(true)
                          .withPrice(15.90)
                          .withCurrency(EnumCurrency.EUR)
                          .build();
    }

}
