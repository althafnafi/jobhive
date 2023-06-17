package com.althaf.jobhive.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Employer {
    @SerializedName("employer_id")
    private int employer_id;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("company_name")
    private String company_name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("address")
    private String address;


    public Employer(int employer_id, String full_name, String company_name, String email, String password, String address) {
        this.employer_id = employer_id;
        this.full_name = full_name;
        this.company_name = company_name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public Employer(String full_name, String company_name, String email, String password, String address) {
        this.full_name = full_name;
        this.company_name = company_name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public Employer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Employer(int employer_id) {
        this.employer_id = employer_id;
    }



    public int getEmployerId() {
        return employer_id;
    }

    public void setEmployerId(int employer_id) {
        this.employer_id = employer_id;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public String getCompanyName() {
        return company_name;
    }

    public void setCompanyName(String company_name) {
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    public String toString () {
        return "{" +
                "employer_id=" + employer_id +
                ", full_name=" + full_name +
                ", company_name=" + company_name +
                ", email=" + email +
                ", password=" + password +
                ", address=" + address +
                '}';
    }

}
