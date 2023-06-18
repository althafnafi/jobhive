package com.althaf.jobhive.request;


import com.althaf.jobhive.model.Employer;
import com.althaf.jobhive.model.Job;
import com.althaf.jobhive.model.JobApplication;
import com.althaf.jobhive.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    /* USERS ENDPOINT */

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{user_id}")
    Call<User> getUserById(
            @Path("user_id") int user_id
    );

    @POST("users/register")
    Call<User> registerUser
            (
                    @Body User userData
            );

    @POST("users/login")
    Call<User> loginUser
            (
                    @Body User userData
            );

    /* EMPLOYERS ENDPOINT */
    @GET("employers")
    Call<List<Employer>> getAllEmployers();

    @GET("employers/{employer_id}")
    Call<Employer> getEmployerById(
            @Path("employer_id") int employer_id
    );

    @POST("employers/register")
    Call<Employer> registerEmployer
            (
                    @Body Employer employerData
            );

    @POST("employers/login")
    Call<Employer> loginEmployer
            (
                    @Body Employer employerData
            );

    @GET("jobs/")
    Call<List<Job>> getAllJobs();

    @GET("jobs/{job_id}")
    Call<Job> getJobById(
            @Path("job_id") int job_id
    );
    @GET("jobs/")
    Call<List<Job>> getJobsByEmployerId(
            @Query("employer_id") int employer_id
    );

    @POST("jobs/")
    Call<Job>postJob(
            @Body Job jobData
    );

    @PATCH("jobs/{job_id}")
    Call<Job> updateJob(
            @Path("job_id") int job_id,
            @Body Job jobData
    );

    @POST("jobs/apply")
    Call<Job> applyJob(
            @Body JobApplication jobAppData
    );

    @DELETE("jobs/{job_id}")
    Call<Job> deleteJob(
            @Path("job_id") int job_id
    );
}
