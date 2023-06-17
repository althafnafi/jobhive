package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.althaf.jobhive.adapter.JobsRecyclerViewAdapter;
import com.althaf.jobhive.adapter.JobsRecyclerViewInterface;
import com.althaf.jobhive.model.Employer;
import com.althaf.jobhive.model.Job;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerDashboard extends AppCompatActivity implements JobsRecyclerViewInterface {

    private String TAG = "DEBUG_DATA";
    BaseApiService apiServ;
    RecyclerView jobsRecyclerView;
    CardView backBtn;
    TextView employerName, employerEmail, employerAddress, welcomeText;
    Context ctx;
    Employer currEmployer;
    ArrayList<Job> currEmpJobs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_dashboard);

        jobsRecyclerView = findViewById(R.id.jobsRecyclerView);
        welcomeText = findViewById(R.id.welcomeText);
        backBtn = findViewById(R.id.backBtnCard);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        backBtn.setOnClickListener(view -> {
            finish();
        });

//        currEmployer = new Employer(getIntent().getIntExtra("employerId", -1));
//        Log.d(TAG, "onCreate: " + currEmployer);
//
//
//        if (currEmployer.getEmployerId() == -1) {
//            Log.d(TAG, "error: " + "Employer ID is -1");
//            return;
//        }
//        // Get data from backend
//        reqEmployerById(currEmployer.getEmployerId());
    }

    private void getData() {
        currEmployer = new Employer(getIntent().getIntExtra("employerId", -1));
        Log.d(TAG, "onCreate: " + currEmployer);


        if (currEmployer.getEmployerId() == -1) {
            Log.d(TAG, "error: " + "Employer ID is -1");
            return;
        }
        // Get data from backend
        reqEmployerById(currEmployer.getEmployerId());
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void inflateRecyclerView() {;
        // Inflate data to views
        Log.d(TAG, "currEmpJobs: " + currEmpJobs);
        JobsRecyclerViewAdapter adapter = new JobsRecyclerViewAdapter(ctx, currEmpJobs, this);
        jobsRecyclerView.setAdapter(adapter);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
    }
    protected void reqEmployerById(int employerId) {
        apiServ.getEmployerById(employerId).enqueue(new Callback<Employer>() {
            @Override
            public void onResponse(Call<Employer> call, Response<Employer> response) {
                if (response.isSuccessful()) {
                    Employer respEmployer = response.body();
                    if (respEmployer == null) {
                        Log.d(TAG, "onResponse: " + "Employer is null");
                        return;
                    }
                    currEmployer = respEmployer;
                    Log.d(TAG, "onResponse: " + currEmployer);
                    Log.d(TAG, "onCreate: " + currEmployer);
                    welcomeText.setText("Welcome,\n" + currEmployer.getFullName() + " !");
                    reqJobsByEmployerId(currEmployer.getEmployerId());
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Employer> call, Throwable t) {
                Log.d(TAG, "onFailure (1): " + t.getMessage());
            }
        });
    }

    protected void reqJobsByEmployerId(int employerId) {
        apiServ.getJobsByEmployerId(employerId).enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d(TAG, "onResponse [fail]: " + response.message());
                    return;
                }
                currEmpJobs = (ArrayList<Job>) response.body();

                for (Job job: currEmpJobs) {
                    job.setCompanyName(currEmployer.getCompanyName());
                }

                Log.d(TAG, "onResponse: " + currEmpJobs);
                inflateRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Log.d(TAG, "onFailure (2): " + t.getMessage());
            }
        });
    }

    protected void reqPostNewJob() {

    }

    protected void reqJobById() {

    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: " + position);
        Log.d(TAG, "onItemClick: " + currEmpJobs.get(position));
        Intent moveToDetails = new Intent(ctx, JobDetails.class);
        moveToDetails.putExtra("jobId", currEmpJobs.get(position).getJobId());
        moveToDetails.putExtra("employerId", currEmployer.getEmployerId());
        startActivity(moveToDetails);
    }
}