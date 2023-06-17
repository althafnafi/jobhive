package com.althaf.jobhive.model;

import com.google.gson.annotations.SerializedName;

public class JobApplication {
    // app_id | job_id | user_id | created_at | message | status
    @SerializedName("app_id")
    private int app_id;
    @SerializedName("job_id")
    private int job_id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private AppStatus status;

    public JobApplication(int app_id, int job_id, int user_id, String created_at, String message, AppStatus status) {
        this.app_id = app_id;
        this.job_id = job_id;
        this.user_id = user_id;
        this.created_at = created_at;
        this.message = message;
        this.status = status;
    }

    public JobApplication(int app_id, int job_id, int user_id, String message) {
        this.app_id = app_id;
        this.job_id = job_id;
        this.user_id = user_id;
        this.message = message;
    }

    public JobApplication(int job_id, int user_id, String message) {
        this.job_id = job_id;
        this.user_id = user_id;
        this.message = message;
    }

    public int getAppId() {
        return app_id;
    }

    public void setAppId(int app_id) {
        this.app_id = app_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJobId(int job_id) {
        this.job_id = job_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppStatus getStatus() {
        return status;
    }

    public String getStatusString() {
        return status.toString();
    }

    public void setStatus(AppStatus status) {
        this.status = status;
    }
}
