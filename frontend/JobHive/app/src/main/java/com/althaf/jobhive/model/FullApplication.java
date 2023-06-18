package com.althaf.jobhive.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class FullApplication {
    @SerializedName("job_id")
    private int job_id;
    @SerializedName("job_title")
    private String job_title;
    @SerializedName("job_cat")
    private String job_cat;
    @SerializedName("job_desc")
    private String job_desc;
    @SerializedName("salary_avg")
    private double salary_avg;
    @SerializedName("job_req")
    private String job_req;
    @SerializedName("employer_id")
    private int employer_id;
    @SerializedName("city")
    private String city;
    @SerializedName("created_at")
    private Timestamp applied_at;
    @SerializedName("app_id")
    private int app_id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private AppStatus status;
}
