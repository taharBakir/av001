package com.clusterpi.dto;


public class ShippingDto {
    private final long id;

    private ShippingDto(Builder builder) {
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

        public ShippingDto build() {
            return new ShippingDto(this);
        }
    }
}
