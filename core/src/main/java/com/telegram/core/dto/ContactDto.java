package com.telegram.core.dto;

public class ContactDto {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String vcard;
    private long userId;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setVcard(String vcard) {
        this.vcard = vcard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getVcard() {
        return vcard;
    }

    public long getUserId() {
        return userId;
    }
}
