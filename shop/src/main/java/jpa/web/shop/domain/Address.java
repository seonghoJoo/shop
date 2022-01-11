package jpa.web.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

// JPA 내장 타입
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
