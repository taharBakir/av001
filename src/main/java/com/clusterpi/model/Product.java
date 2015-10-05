package com.clusterpi.model;

import com.clusterpi.util.EnumCurrency;

public class Product {
  private final long id;
  private final String name;
  private final String description;
  private final boolean available;
  private final boolean online;
  private final double price;
  private final EnumCurrency currency;

  public static class Builder {
    private final long id;
    private String name;
    private String description;
    private boolean available;
    private boolean online;
    private double price;
    private EnumCurrency currency;

    public Builder(long id){
      this.id = id;
    }

    public Builder withName(String name){
      this.name = name ;
      return this ;
    }

    public Builder withDescription(String description){
      this.description = description ;
      return this ;
    }

    public Builder isAvailabable(boolean available){
      this.available = available ;
      return this;
    }

    public Builder isOnline(boolean online){
      this.online = online ;
      return this;
    }

    public Builder withPrice(double price){
      this.price = price ;
      return this;
    }

    public Builder withCurrency(EnumCurrency currency){
      this.currency = currency ;
      return this;
    }

    public Product build(){
      return new Product(this);
    }
  }


  private Product(Builder b){
    this.id = b.id ;
    this.name = b.name ;
    this.description = b.description ;
    this.available = b.available ;
    this.online = b.online ;
    this.price = b.price ;
    this.currency = b.currency ;
  } 


  public long getId(){
    return this.id ;
  }

  public String getName(){
    return this.name;
  }

  public String getDescription(){
    return this.description;
  }

  public boolean isAvailable(){
    return this.available;
  }

  public boolean isOnline(){
    return this.online;
  }

  public double getPrice(){
    return this.price;
  }

  public String getCurrency(){
    return this.currency.toString();
  }

}
