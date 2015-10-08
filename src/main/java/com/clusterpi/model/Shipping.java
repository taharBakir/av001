package com.clusterpi.model;


public class Shipping {
    private final long id;

    private Shipping(Builder builder) {
        this.id = builder.id;
    }

    public long getId() {
        return this.id;
    }

    public static class Builder {
        private final long id;

        public Builder(long id) {
            this.id = id;
        }

        public Shipping build() {
            return new Shipping(this);
        }
    }
}
