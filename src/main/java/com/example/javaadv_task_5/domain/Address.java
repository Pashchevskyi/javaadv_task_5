package com.example.javaadv_task_5.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_has_active")
    private Boolean addressHasActive = Boolean.TRUE;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    public static class Builder {
        private Long id;

        private Boolean addressHasActive = Boolean.TRUE;

        private String country;

        private String city;

        private String street;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder addressHasActive(Boolean addressHasActive) {
            this.addressHasActive = addressHasActive;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    public Address() {

    }

    public static Builder builder() {
        return new Builder();
    }

    private Address(Builder builder) {
        this.id = builder.id;
        this.addressHasActive = builder.addressHasActive;
        this.country = builder.country;
        this.city = builder.city;
        this.street = builder.street;
    }
}
