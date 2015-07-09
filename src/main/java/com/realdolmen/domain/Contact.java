package com.realdolmen.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by cda5732 on 1/07/2015.
 */
@Entity
public class Contact implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;

    @Column(length = 55, nullable = false)
    @NotBlank
    @Size(max = 55)
    private String firstName;

    @Column(length = 55, nullable = false)
    @NotBlank
    @Size(max = 55)
    private String lastName;

    @Embedded
    private Address address=new Address();

    @ElementCollection
    private List<String> emailAddresses;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Gender gender;

    @ElementCollection
    private List<String> phoneNumbers;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull
    @Past
    private Date birthday;

    @Column(length = 2000)
    @Size(max = 2000)
    private String description;

    @Min(1)
    @Max(5)
    private int likeness = 1;

    @ManyToOne
    private Group contactGroup;

    public Contact() {
    }

    public Contact(String firstName, String lastName, Gender gender, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeness() {
        return likeness;
    }

    public void setLikeness(int likeness) {
        this.likeness = likeness;
    }

    public Group getContactGroup() {
        return contactGroup;
    }

    public void setContactGroup(Group contactGroup) {
        this.contactGroup = contactGroup;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactGroup=" + contactGroup +
                '}';
    }
}
