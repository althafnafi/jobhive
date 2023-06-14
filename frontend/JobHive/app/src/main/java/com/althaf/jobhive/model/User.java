package com.althaf.jobhive.model;


import com.google.gson.annotations.SerializedName;

public class User{
    @SerializedName("user_id")
    public int user_id;
    @SerializedName("full_name")
    public String full_name;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("address")
    public String address;

    public User(int user_id, String full_name, String email, String password, String address) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public String toString() {
        return "{" +
                "user_id=" + user_id +
                ", full_name=" + full_name +
                ", email=" + email +
                ", password=" + password +
                ", address=" + address +
                '}';
    }
}
