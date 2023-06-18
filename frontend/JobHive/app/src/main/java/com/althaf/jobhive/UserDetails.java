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

import com.althaf.jobhive.adapter.JobsRecyclerViewAdapter;
import com.althaf.jobhive.adapter.JobsRecyclerViewInterface;
import com.althaf.jobhive.model.Job;
import com.althaf.jobhive.model.User;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetails extends AppCompatActivity implements JobsRecyclerViewInterface {
    private String TAG = "DEBUG_DATA";
    BaseApiService apiServ;
    RecyclerView jobsRecyclerView;
    JobsRecyclerViewAdapter adapter;
    CardView closeBtn, jobsAppliedForBtn, jobsBookmarkedBtn;
    Context ctx;
    User currUser;
    ArrayList<Job> jobsAppliedFor = new ArrayList<>();
    ArrayList<Job> jobsBookmarked = new ArrayList<>();
    TextView fullNameTv, emailTv, addressTv, numJobsAppliedTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        jobsBookmarkedBtn = findViewById(R.id.jobsBookmarkedBtnUser);
        jobsAppliedForBtn = findViewById(R.id.jobsAppliedBtnUser);
        closeBtn = findViewById(R.id.closeBtnUser);
        fullNameTv = findViewById(R.id.fullNameTextUser);
        emailTv = findViewById(R.id.emailTextUser);
        addressTv = findViewById(R.id.addressTextUser);
        numJobsAppliedTv = findViewById(R.id.numJobsAppliedUser);
        jobsRecyclerView = findViewById(R.id.jobsRecyclerViewUser);

        closeBtn.setOnClickListener(view -> {
            Log.d(TAG, "close btn clicked");
            finish();
        });

        jobsAppliedForBtn.setOnClickListener(view -> {
            Log.d(TAG, "jobsApplied clicked");
        });

        jobsBookmarkedBtn.setOnClickListener(view -> {
            Log.d(TAG, "jobsBookmarked clicked");
        });

    }

    private void updateText() {
        fullNameTv.setText(currUser.getFullName());
        emailTv.setText(currUser.getEmail());
        addressTv.setText(currUser.getAddress());
    }

    private void getData() {
        int currUserId = getIntent().getIntExtra("userId", -1);
        reqGetUserById(currUserId);
        reqGetAppliedJobsByUserId(currUserId);
        return;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    protected void reqGetUserById(int user_id) {
        apiServ.getUserById(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d("DEBUG_DATA", "fail: getUserById -> " + response.code());
                    return;
                }

                currUser = response.body();
                updateText();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("DEBUG_DATA", "fail: getUserById -> " + t.toString());
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new JobsRecyclerViewAdapter(ctx, jobsAppliedFor, this);
        jobsRecyclerView.setAdapter(adapter);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
    }

    protected void reqGetAppliedJobsByUserId(int userId) {
        apiServ.getAppliedJobsByUserId(userId, "true").enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d("DEBUG_DATA", "fail: getAppliedJobsByUserId -> " + response.code());
                    return;
                }

                jobsAppliedFor = (ArrayList<Job>) response.body();
                numJobsAppliedTv.setText(String.valueOf(jobsAppliedFor.size()));
                Log.d(TAG, "" + response.body());
                setupRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Log.d("DEBUG_DATA", "fail: getAppliedJobsByUserId -> " + t.toString());
            }
        });

    }

    protected void reqGetJobsByUserBookmarks(int userId) {

    }

    @Override
    public void onItemClick(int position) {
        Intent moveToDetails = new Intent(ctx, JobDetails.class);
        moveToDetails.putExtra("jobId", jobsAppliedFor.get(position).getJobId());
        moveToDetails.putExtra("employerId", jobsAppliedFor.get(position).getEmployerId());
        moveToDetails.putExtra("userId", currUser.getUserId());
        startActivity(moveToDetails);
    }
}