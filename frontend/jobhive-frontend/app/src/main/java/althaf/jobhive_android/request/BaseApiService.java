package althaf.jobhive_android.request;

import althaf.jobhive_android.model.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {
    @POST("/users/register")
    Call<User> registerUser
            (
                    @Query("email") String email,
                    @Query("password") String password
            );

//    @GET("/users")
//    Call<User> getAllUsers
//            (
//
//            );
}
