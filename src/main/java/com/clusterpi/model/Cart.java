package com.clusterpi.model;


import java.time.Instant;

public class Cart {
    private final long id;
    private final Instant date;

    private Cart(Builder builder) {
        this.id = builder.id;
        this.date = builder.date;
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

        public Builder(long id) {
            this.id = id;
        }

        public Builder withDate(Instant date) {
            this.date = date;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }

}
