package com.clusterpi.dto;


import java.util.HashMap;

public class UserDto {
    private final long id;
    private final String name;
    private final HashMap<Long, AddressDto> addresses;

    private UserDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.addresses = new HashMap<Long, AddressDto>(builder.addresses);
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
        private HashMap<Long, AddressDto> addresses;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAddresses(HashMap<Long, AddressDto> addresses) {
            this.addresses = new HashMap<Long, AddressDto>(addresses);
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }

}
