package com.clusterpi.model;


public class Payment {
    private final long id;

    private Payment(Builder builder) {
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

        public Payment build() {
            return new Payment(this);
        }
    }

}
