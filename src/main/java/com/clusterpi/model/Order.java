package com.clusterpi.model;


import java.time.Instant;

import com.clusterpi.model.Address;

import com.clusterpi.model.User;

public class Order {
    private final long id;
    private final Instant date;
    private final Address shippingAddress;
    private final Address invoiceAddress;
    private final User customer;

    private Order(Builder builder) {
        this.id = builder.id;
        this.date = builder.date;
        this.shippingAddress = builder.shippingAddress;
        this.invoiceAddress = builder.invoiceAddress;
        this.customer = builder.customer;
    }

    public long getId() {
        return this.id;
    }

    public Instant getDate() {
        return this.date;
    }

    public static class Builder {
        private final long id;
        private Instant date;
        private Address shippingAddress;
        private Address invoiceAddress;
        private User customer;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withDate(Instant date) {
            this.date = date;
            return this;
        }

        public Builder withInvoiceAddress(Address invoiceAddress) {
            this.invoiceAddress = invoiceAddress;
            return this;
        }

        public Builder withShippingAddress(Address shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Builder withCustomer(User customer) {
            this.customer = customer;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

}
