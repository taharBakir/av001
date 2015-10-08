package com.clusterpi.model;

import java.math.BigDecimal;

public class LineItem {
    private final long id;
    private final BigDecimal price;
    private final int quantity;

    private LineItem(Builder builder) {
        this.id = builder.id;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    public long getId() {
        return this.id;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public static class Builder {
        private final long id;
        private BigDecimal price;
        private int quantity;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public LineItem build() {
            return new LineItem(this);
        }
    }

}
