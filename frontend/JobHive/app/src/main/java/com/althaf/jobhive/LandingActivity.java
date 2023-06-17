package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

public class LandingActivity extends AppCompatActivity {

    BaseApiService apiServ;
    Button userBtn, employerBtn;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        userBtn = findViewById(R.id.userBtnLanding);
        employerBtn = findViewById(R.id.employeeBtnLanding);

        userBtn.setOnClickListener(view -> {
            Intent move = new Intent(LandingActivity.this, RegisterActivity.class);
            move.putExtra("accType", "user");
            startActivity(move);
        });

        employerBtn.setOnClickListener(view -> {
            Intent move = new Intent(LandingActivity.this, EmployerRegActivity.class);
            move.putExtra("accType", "employer");
            startActivity(move);
        });
    }
}