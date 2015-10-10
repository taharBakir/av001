package com.clusterpi.dto;


import java.time.Instant;

public class OrderDto {
    private final long id;
    private final Instant date;
    private final AddressDto shippingAddressDto;
    private final AddressDto invoiceAddressDto;
    private final UserDto customer;

    private OrderDto(Builder builder) {
        this.id = builder.id;
        this.date = builder.date;
        this.shippingAddressDto = builder.shippingAddressDto;
        this.invoiceAddressDto = builder.invoiceAddressDto;
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
        private AddressDto shippingAddressDto;
        private AddressDto invoiceAddressDto;
        private UserDto customer;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withDate(Instant date) {
            this.date = date;
            return this;
        }

        public Builder withInvoiceAddress(AddressDto invoiceAddressDto) {
            this.invoiceAddressDto = invoiceAddressDto;
            return this;
        }

        public Builder withShippingAddress(AddressDto shippingAddressDto) {
            this.shippingAddressDto = shippingAddressDto;
            return this;
        }

        public Builder withCustomer(UserDto customer) {
            this.customer = customer;
            return this;
        }

        public OrderDto build() {
            return new OrderDto(this);
        }
    }

}
