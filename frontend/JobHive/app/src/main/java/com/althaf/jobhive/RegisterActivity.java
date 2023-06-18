package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.althaf.jobhive.model.User;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    BaseApiService apiServ;
    TextView bypassLogin;
    EditText nameBox, emailBox, passBox;
    Button confirmRegBtn;

    Context ctx;

    String ACCOUNT_TYPE = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        // Define components
        nameBox = findViewById(R.id.nameBoxReg);
        emailBox = findViewById(R.id.emailBoxReg);
        passBox = findViewById(R.id.passBoxReg);
        confirmRegBtn = findViewById(R.id.regButtonReg);
        bypassLogin = findViewById(R.id.loginBtnReg);

        confirmRegBtn.setOnClickListener(view -> {
            // Call API to register
            reqRegister();
            clearFields();
        });

        bypassLogin.setOnClickListener(view -> {
            Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            moveToLogin.putExtra("accType", ACCOUNT_TYPE);
            startActivity(moveToLogin);
        });
    }

    private void clearFields() {
        nameBox.setText("");
        emailBox.setText("");
        passBox.setText("");
    }
    protected void reqRegister() {
        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();
        String password = passBox.getText().toString();


        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(ctx, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        User user_body = new User(
                name,
                email,
                password
        );

        Log.d("DEBUG_DATA", String.valueOf(user_body));

        apiServ.registerUser(
                user_body
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user == null) {
                        Toast.makeText(ctx, "User is null", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(ctx, "Registration successful", Toast.LENGTH_LONG).show();
                    Log.d("DEBUG_DATA", user.toString());
                } else {
                    Log.d("DEBUG_DATA", "fail: registerUser");
                    Toast.makeText(ctx, "Registration failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("DEBUG_DATA", "fail: registerUser -> " + t.toString());
                Toast.makeText(ctx, "Registration failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void reqGetAllUsers() {
        apiServ.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG_DATA", "success: getAllUsers");
                    Toast.makeText(ctx, "Successfully registered account", Toast.LENGTH_LONG).show();
                    List<User> users = response.body();
                    if (users == null) {
                        Log.d("DEBUG_DATA", "users is null");
                        return;
                    }

                    for (User user : users) {
                        Log.d("DEBUG_DATA", user.toString());
                        Toast.makeText(ctx, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("DEBUG_DATA", "fail: getAllUsers");
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("DEBUG_DATA", "fail: getAllUsers -> " + t.toString());
            }
        });
    }
}