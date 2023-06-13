package com.althaf.jobhive.request;


import com.althaf.jobhive.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {
    // Register a user
    @POST("/users/register")
    Call<User> registerUser
            (
                    @Query("email") String email,
                    @Query("password") String password
            );
    // Get all registered users
    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/users/1")
    Call<List<User>> getFirstUser();

}
