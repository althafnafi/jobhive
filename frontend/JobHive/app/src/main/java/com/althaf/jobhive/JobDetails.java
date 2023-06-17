package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.althaf.jobhive.model.Job;
import com.althaf.jobhive.model.JobApplication;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetails extends AppCompatActivity {

    BaseApiService apiServ;
    Context ctx;
    CardView editJobBtn;
    Job currJob;
    private int currUserId = -1;
    private int currJobId = -1;
    TextView jobTitleTv, companyNameTv, jobDescTv, jobReqTv, bottomBtnTv,
            cityLocationTv, jobCatTv, avgSalaryTv, lastUpdatedTv;
    Button bottomBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        editJobBtn = findViewById(R.id.editJobBtnDetails);
        jobTitleTv = findViewById(R.id.jobTitleDetails);
        companyNameTv = findViewById(R.id.companyNameDetails);
        jobDescTv = findViewById(R.id.jobDescDetails);
        jobReqTv = findViewById(R.id.jobReqDetails);
        cityLocationTv = findViewById(R.id.cityLocationDetails);
        jobCatTv = findViewById(R.id.jobCatTextDetails);
        avgSalaryTv = findViewById(R.id.avgSalaryDetails);
        lastUpdatedTv = findViewById(R.id.lastUpdatedDetails);
        bottomBtnTv = findViewById(R.id.bottomBtnText);
        bottomBtn = findViewById(R.id.bottomBtn);

        currJobId = getIntent().getIntExtra("jobId", -1);
        currUserId = getIntent().getIntExtra("userId", -1);

        reqGetJobById(currJobId);
        if (!isUser()) {
            editJobBtn.setVisibility(CardView.VISIBLE);
            editJobBtn.setOnClickListener(view -> {
                // Move to edit job activity
                Log.d(getString(R.string.log_str), "editJobBtn clicked");
            });
            bottomBtnTv.setText("DELETE JOB");
            bottomBtn.setBackgroundColor(bottomBtn.getContext().getResources().getColor(R.color.light_red));
        } else {
            editJobBtn.setVisibility(CardView.INVISIBLE);
        }

        bottomBtn.setOnClickListener(view -> {
            if (isUser())
                applyJob(currJobId, currUserId, "No message");
            else
                reqDeleteJobById(currJobId);
        });


    }

    protected boolean isUser() {
        return currUserId != -1;
    }

    protected void applyJob(int jobId, int userId, String message) {
        if (jobId == -1 || userId == -1) {
            Log.d("DEBUG_DATA", "error: " + "jobId or userId is -1");
            Toast.makeText(ctx, "You can't apply for this job", Toast.LENGTH_LONG).show();
            return;
        }
        JobApplication newJobApp = new JobApplication(jobId, userId, message);

        apiServ.applyJob(newJobApp).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d("DEBUG_DATA", "failed: " + response.code());
                    return;
                }
                Job respJob = response.body();
                Log.d("DEBUG_DATA", "respJob: " + respJob);
                currJob = respJob;
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Log.d("DEBUG_DATA", "onFailure: " + t.getMessage());
            }
        });
    }

    protected void reqGetJobById(int jobId) {
        apiServ.getJobById(jobId).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d("DEBUG_DATA", "failed: " + response.code());
                    return;
                }
                Job respJob = response.body();
                Log.d("DEBUG_DATA", "respJob: " + respJob);
                currJob = respJob;
                updateVal();
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Log.d("DEBUG_DATA", "onFailure: " + t.getMessage());
            }
        });
    }

    protected void reqDeleteJobById(int jobId) {
        apiServ.deleteJob(jobId).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d("DEBUG_DATA", "failed: " + response.code());
                    return;
                }
                Job respJob = response.body();
                Log.d("DEBUG_DATA", "respJob: " + respJob);
                currJob = respJob;
//                Intent intent = new Intent(JobDetails.this, EmployerDashboard.class);
//                int employerId = getIntent().getIntExtra("employerId", -1);
//                Log.d("DEBUG_DATA", "val:" + String.valueOf(employerId));
//                intent.putExtra("employerId", intent.getIntExtra("employerId", -1));
//                startActivity(intent);

                Toast.makeText(ctx, "Job deleted successfully", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Log.d("DEBUG_DATA", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reqGetJobById(currJobId);
    }

    private void updateVal() {
        jobTitleTv.setText(currJob.getJobTitle());
        companyNameTv.setText(currJob.getCompanyName());
        jobDescTv.setText(currJob.getJobDesc());
        jobReqTv.setText(currJob.getJobReq());
        cityLocationTv.setText(currJob.getCity());
        jobCatTv.setText(currJob.getJobCat());
        avgSalaryTv.setText("$" + currJob.getSalaryAvg() +"/yr");
        lastUpdatedTv.setText("Created " + currJob.getCreatedAtDiff() + " days ago");
    }
}