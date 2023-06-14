package com.althaf.jobhive.request;


import com.althaf.jobhive.model.Employer;
import com.althaf.jobhive.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    Call<User> loginEmployer
            (
                    @Body Employer employerData
            );
}
