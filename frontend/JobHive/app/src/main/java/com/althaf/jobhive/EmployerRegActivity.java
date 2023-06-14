package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.althaf.jobhive.model.Employer;
import com.althaf.jobhive.model.User;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerRegActivity extends AppCompatActivity {
    BaseApiService apiServ;
    EditText nameBox, emailBox, passBox, companyNameBox, addressBox;
    Button confirmRegBtn;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_reg);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        // Define components
        nameBox = findViewById(R.id.nameBoxEmpReg);
        companyNameBox = findViewById(R.id.companyNameBoxEmpReg);
        emailBox = findViewById(R.id.emailBoxEmpReg);
        passBox = findViewById(R.id.passBoxEmpReg);
        addressBox = findViewById(R.id.addressBoxEmpReg);
        confirmRegBtn = findViewById(R.id.regButtonEmpReg);

        confirmRegBtn.setOnClickListener(view -> {
            // Call API to register employer
            reqRegisterEmployer();
            clearFields();
        });
    }

    private void clearFields() {
        nameBox.setText("");
        companyNameBox.setText("");
        emailBox.setText("");
        passBox.setText("");
        addressBox.setText("");
    }

    protected void reqRegisterEmployer() {
        String name = nameBox.getText().toString();
        String companyName = companyNameBox.getText().toString();
        String email = emailBox.getText().toString();
        String password = passBox.getText().toString();
        String address = addressBox.getText().toString();

        Employer employer = new Employer(name, companyName, email, password, address);
        apiServ.registerEmployer(employer).enqueue(new Callback<Employer>() {
            @Override
            public void onResponse(Call<Employer> call, Response<Employer> response) {
                if (response.isSuccessful()) {
                    Employer employer = response.body();
                    Toast.makeText(ctx, "Registered successfully", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG_DATA", employer.toString());
                } else {
                    Toast.makeText(ctx, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employer> call, Throwable t) {
                Toast.makeText(ctx, "Registration failed", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG_DATA", t.getMessage());
            }
        });
    }
}