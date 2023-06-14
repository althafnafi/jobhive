package com.althaf.jobhive.request;


import com.althaf.jobhive.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    // Register a user
    @POST("api/users/register")
    Call<User> registerUser
            (
                    @Query("email") String email,
                    @Query("password") String password
            );
    // Get all registered users
    @GET("api/users")
    Call<List<User>> getAllUsers();

    @GET("api/users/{user_id}")
    Call<User> getUserById(
            @Path("user_id") int user_id
    );

}
