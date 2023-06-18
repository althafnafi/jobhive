package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.althaf.jobhive.model.Job;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateJobs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Context ctx;
    private final String TAG = "DEBUG_DATA";
    BaseApiService apiServ;

    Button postJobBtn;
    Spinner citySpinner, jobCategorySpinner;
    TextView jobTitle, jobDesc, jobAvgSalary,jobReq;

    int currEmployerId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_jobs);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        citySpinner = findViewById(R.id.citySpinnerJobs);
        jobCategorySpinner = findViewById(R.id.jobCatSpinnerJobs);
        jobTitle = findViewById(R.id.jobTitleJobs);
        jobDesc = findViewById(R.id.jobDescJobs);
        jobAvgSalary = findViewById(R.id.avgSalaryJobs);
        jobReq = findViewById(R.id.jobReqJobs);
        postJobBtn = findViewById(R.id.postJobBtn);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(ctx, R.array.job_cities, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> jobCatAdapter = ArrayAdapter.createFromResource(ctx, R.array.job_categories, android.R.layout.simple_spinner_item);
        jobCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobCategorySpinner.setAdapter(jobCatAdapter);
        jobCategorySpinner.setOnItemSelectedListener(this);

        currEmployerId = getIntent().getIntExtra("employerId", -1);

        postJobBtn.setOnClickListener(view -> {
            Log.d(TAG, "Clicked");

            String jobTitleStr = jobTitle.getText().toString();
            String jobDescStr = jobDesc.getText().toString();
            Double jobAvgSalaryNum = Double.parseDouble(jobAvgSalary.getText().toString());
            String jobReqStr = jobReq.getText().toString();
            String city = citySpinner.getSelectedItem().toString();
            String jobCat = jobCategorySpinner.getSelectedItem().toString();

            if (jobTitleStr.isEmpty() || jobDescStr.isEmpty() || jobAvgSalaryNum == 0
                    || jobReqStr.isEmpty() || city.isEmpty() || jobCat.isEmpty()) {
                Log.d(TAG, "Empty fields");
                return;
            }

            reqPostNewJob(new Job(
                    jobTitleStr,
                    jobCat,
                    jobDescStr,
                    jobAvgSalaryNum,
                    jobReqStr,
                    currEmployerId,
                    city
            ));
        });
    }


    protected void reqPostNewJob(Job newJob) {
        Log.d(TAG, "reqPostNewJob: " + newJob.toString());
        apiServ.postJob(newJob).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    finish();
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "Selected: " + adapterView.getItemAtPosition(i).toString());
        String selected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}