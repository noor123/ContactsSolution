package com.realdolmen.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by cda5732 on 1/07/2015.
 */
@Embeddable
public class Address implements Serializable {
    @Column(length = 255)
    @Size(max = 255)
    private String street1;
    @Column(length = 255)
    @Size(max = 255)
    private String street2;
    @Column(length = 50)
    @Size(max = 50)
    private String zipCode;
    @Column(length = 50)
    @Size(max = 50)
    private String city;
    @Column(length = 50)
    @Size(max = 50)
    private String country;

    public Address() {
    }

    public Address(String street1, String street2, String zipCode, String city, String country) {
        this.street1 = street1;
        this.street2 = street2;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
