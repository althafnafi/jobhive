package com.althaf.jobhive.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Job {
    /*
            "job_id": 5,
        "job_title": "Junior Client Engineer",
        "job_cat": "Junior Client Engineering",
        "job_desc": "A junior client engineer that will focus on handling clients, sorting as well as planning projects based on the client's request.",
        "salary_avg": "45000",
        "job_req": "Comfortable with handling clients, and have a background in software engineering",
        "employer_id": 10
     */

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
    private Timestamp created_at;
    private String company_name = "";

    public Job() {

    }

    public Job(int job_id, String job_title, String job_cat, String job_desc, double salary_avg, String job_req, int employer_id, String city, Timestamp created_at) {
        this.job_id = job_id;
        this.job_title = job_title;
        this.job_cat = job_cat;
        this.job_desc = job_desc;
        this.salary_avg = salary_avg;
        this.job_req = job_req;
        this.employer_id = employer_id;
        this.created_at = created_at;
        this.city = city;
    }

    public Job(String job_title, String job_cat, String job_desc, double salary_avg, String job_req, int employer_id, String city) {
        this.job_title = job_title;
        this.job_cat = job_cat;
        this.job_desc = job_desc;
        this.salary_avg = salary_avg;
        this.job_req = job_req;
        this.employer_id = employer_id;
        this.city = city;
    }


    public Job(double salary_avg) {
        this.salary_avg = salary_avg;
    }

    public String getCompanyName() {
        return company_name;
    }

    public void setCompanyName(String company_name) {
        this.company_name = company_name;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public long getCreatedAtDiff() {
        long currentTimeMillis = System.currentTimeMillis();
        long timestampMillis = this.created_at.getTime();
        long differenceMillis = currentTimeMillis - timestampMillis;
        return TimeUnit.MILLISECONDS.toDays(differenceMillis);
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getJobId() {
        return job_id;
    }

    public void setJobId(int job_id) {
        this.job_id = job_id;
    }

    public String getJobTitle() {
        return job_title;
    }

    public void setJobTitle(String job_title) {
        this.job_title = job_title;
    }

    public String getJobCat() {
        return job_cat;
    }

    public void setJobCat(String job_cat) {
        this.job_cat = job_cat;
    }

    public String getJobDesc() {
        return job_desc;
    }

    public void setJobDesc(String job_desc) {
        this.job_desc = job_desc;
    }

    public double getSalaryAvg() {
        return salary_avg;
    }

    public void setSalaryAvg(double salary_avg) {
        this.salary_avg = salary_avg;
    }

    public String getJobReq() {
        return job_req;
    }

    public void setJobReq(String job_req) {
        this.job_req = job_req;
    }

    public int getEmployerId() {
        return employer_id;
    }

    public void setEmployerId(int employer_id) {
        this.employer_id = employer_id;
    }

    @NonNull
    public String toString() {
        return "Job{" +
                "job_id=" + job_id +
                ", job_title='" + job_title + '\'' +
                ", job_cat='" + job_cat + '\'' +
                ", job_desc='" + job_desc + '\'' +
                ", salary_avg=" + salary_avg +
                ", job_req='" + job_req + '\'' +
                ", employer_id=" + employer_id +
                ", created_at=" + created_at +
                ", city='" + city + '\'' +
                ", company_name='" + company_name + '\'' +
                '}';
    }

}
