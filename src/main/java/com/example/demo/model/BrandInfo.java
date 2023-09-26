package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandInfo {
    private String brandEntName;
    private String brandCode;
    private String industry;
    private String contacts;
    private String telephone;
    private String contactsPost;

    // Default constructor for Jackson
    public BrandInfo() {}

    // All args constructor
    public BrandInfo(String brandEntName, String brandCode, String industry, String contacts, String telephone, String contactsPost) {
        this.brandEntName = brandEntName;
        this.brandCode = brandCode;
        this.industry = industry;
        this.contacts = contacts;
        this.telephone = telephone;
        this.contactsPost = contactsPost;
    }

    @Override
    public String toString() {
        return "BrandInfo{" +
            "brandEntName='" + brandEntName + '\'' +
            ", brandCode='" + brandCode + '\'' +
            ", industry='" + industry + '\'' +
            ", contacts='" + contacts + '\'' +
            ", telephone='" + telephone + '\'' +
            ", contactsPost='" + contactsPost + '\'' +
            '}';
    }

    // Getters and setters for all fields...

    public String getBrandEntName() {
        return brandEntName;
    }

    public void setBrandEntName(String brandEntName) {
        this.brandEntName = brandEntName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactsPost() {
        return contactsPost;
    }

    public void setContactsPost(String contactsPost) {
        this.contactsPost = contactsPost;
    }
}
