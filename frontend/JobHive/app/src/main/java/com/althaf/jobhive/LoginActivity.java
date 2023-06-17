package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.althaf.jobhive.model.Employer;
import com.althaf.jobhive.model.User;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    BaseApiService apiServ;
    Button loginBtn;
    TextView changeAccType;
    EditText emailBox, passBox;
    private String ACCOUNT_TYPE;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiServ = ApiUtils.getApiService();
        ctx = this;

//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtnLogin);
        changeAccType = findViewById(R.id.changeAccLogin);
        emailBox = findViewById(R.id.emailBoxLogin);
        passBox = findViewById(R.id.passBoxLogin);

        // Get account type
        ACCOUNT_TYPE = getIntent().getStringExtra("accType");
        Log.d("DEBUG_DATA", ACCOUNT_TYPE);
        loginBtn.setText("Login as " + ACCOUNT_TYPE);


        loginBtn.setOnClickListener(view -> {
            // Call API to login
            reqLogin();
            clearFields();
        });
        changeAccType.setOnClickListener(view -> {
            // Move to landing activity
            Intent moveToLanding = new Intent(LoginActivity.this, LandingActivity.class);
            moveToLanding.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(moveToLanding);
        });
    }

    private void clearFields() {
        // Clear fields
        emailBox.setText("");
        passBox.setText("");
    }

    protected void reqLogin() {
        if (ACCOUNT_TYPE.equals("user")) {
            User userData = new User(
                    emailBox.getText().toString(),
                    passBox.getText().toString()
            );

            // Call API to login
            apiServ.loginUser(userData).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body() != null) {
                        // Move to user dashboard
                        User userResp = response.body();
                        Log.d("DEBUG_DATA", userResp.toString());
//                        Intent moveToUserDashboard = new Intent(LoginActivity.this, UserDashboardActivity.class);
//                        startActivity(moveToUserDashboard);
                        Toast.makeText(ctx, "User login successful", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Log.d("DEBUG_DATA", "failed response");
                        Toast.makeText(ctx, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("DEBUG_DATA", t.getMessage());
                    Toast.makeText(ctx, "Login failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else if (ACCOUNT_TYPE.equals("employer")) {
            Employer employerData = new Employer(
                    emailBox.getText().toString(),
                    passBox.getText().toString()
            );
            // Call employer login API
            apiServ.loginEmployer(employerData).enqueue(new Callback<Employer>() {
                @Override
                public void onResponse(Call<Employer> call, Response<Employer> response) {
                    if (response.body() != null) {
                        // Move to employer dashboard
                        Employer employerResp = response.body();
                        Log.d("DEBUG_DATA", response.body().toString());
                        Intent moveToEmployerDashboard = new Intent(LoginActivity.this, EmployerDashboard.class);
                        moveToEmployerDashboard.putExtra("employerId", employerResp.getEmployerId());
                        startActivity(moveToEmployerDashboard);

                        Toast.makeText(ctx, "Employer login successful", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d("DEBUG_DATA", "failed response");
                        Toast.makeText(ctx, "Login failed", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Employer> call, Throwable t) {
                    Log.d("DEBUG_DATA", t.getMessage());
                    Toast.makeText(ctx, "Login failed", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}