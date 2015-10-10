package com.clusterpi.dto;


public class AddressDto {
    private final long id;
    private final String name;

    private AddressDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static class Builder {
        private final long id;
        private String name;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public AddressDto build() {
            return new AddressDto(this);
        }
    }

}
