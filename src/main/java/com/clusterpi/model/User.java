package com.clusterpi.model;


import java.util.HashMap;

import com.clusterpi.model.Address;

public class User {
    private final long id;
    private final String name;
    private final HashMap<Long, Address> addresses;

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.addresses = new HashMap<Long, Address>(builder.addresses);
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
        private HashMap<Long, Address> addresses;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAddresses(HashMap<Long, Address> addresses) {
            this.addresses = new HashMap<Long, Address>(addresses);
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
