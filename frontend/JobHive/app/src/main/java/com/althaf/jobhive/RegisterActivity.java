package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;

import com.althaf.jobhive.model.User;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    BaseApiService apiServ;
    EditText emailBox, passBox;
    Button confirmRegBtn;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        // Define components
        emailBox = findViewById(R.id.emailBoxReg);
        passBox = findViewById(R.id.passBoxReg);
        confirmRegBtn = findViewById(R.id.registerBtnReg);

        confirmRegBtn.setOnClickListener(view -> {
            // Call API to register
            reqGetFirstUser();
            Toast.makeText(ctx,"Clicked", Toast.LENGTH_SHORT).show();
//            Log.d("DEBUG","Clicked");
        });
    }


    protected void requestRegister() {
        String inp_email = emailBox.getText().toString();
        String inp_pass = passBox.getText().toString();
        apiServ.registerUser(
                inp_email,
                inp_pass
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ctx, "Successfully registered a new account", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, "Something weird happened?", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ctx,"Registration failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void reqGetAllUsers() {
        apiServ.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(ctx, "Successful", Toast.LENGTH_SHORT);
//                    List<User> userList = response.body();
//                    Log.d("DEBUG", response.body().toString());

                } else {
                    Toast.makeText(ctx, "Weird", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ctx, "Failed", Toast.LENGTH_SHORT);
                Log.d("DEBUG", "Failed");
            }
        });
    }

    protected void reqGetFirstUser() {
        apiServ.getFirstUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                User firstUser = response.body().get(0);
                Log.d("DEBUG", firstUser.email);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("DEBUG", String.valueOf(t));
            }
        });
    }
}