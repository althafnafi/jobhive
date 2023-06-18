package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.althaf.jobhive.model.Job;
import com.althaf.jobhive.model.User;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetails extends AppCompatActivity {
    private String TAG = "DEBUG_DATA";
    BaseApiService apiServ;
    RecyclerView jobsRecyclerView;
    CardView closeBtn, jobsAppliedForBtn, jobsBookmarkedBtn;
    Context ctx;
    User currUser;
    ArrayList<Job> jobsAppliedFor = new ArrayList<Job>();
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

        closeBtn.setOnClickListener(view -> {
            Log.d(TAG, "close btn clicked");
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
        numJobsAppliedTv.setText(String.valueOf(jobsAppliedFor.size()));
    }

    private void getData() {
        int currUserId = getIntent().getIntExtra("userId", -1);
        reqGetUserById(currUserId);
        reqGetJobsApplicationsByUserId(currUserId);
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

    protected void reqGetJobsApplicationsByUserId(int userId) {
        apiServ.getJobsByApplications(userId).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {

            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {

            }
        });

    }

    protected void reqGetJobsByUserBookmarks(int userId) {

    }
}