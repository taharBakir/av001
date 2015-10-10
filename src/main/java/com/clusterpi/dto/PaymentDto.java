package com.clusterpi.dto;


public class PaymentDto {
    private final long id;

    private PaymentDto(Builder builder) {
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

        public PaymentDto build() {
            return new PaymentDto(this);
        }
    }

}
