package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.althaf.jobhive.adapter.JobsRecyclerViewAdapter;
import com.althaf.jobhive.adapter.JobsRecyclerViewInterface;
import com.althaf.jobhive.model.Job;
import com.althaf.jobhive.model.User;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDashboard extends AppCompatActivity implements JobsRecyclerViewInterface {

    private final String TAG = "DEBUG_DATA";
    BaseApiService apiServ;
    RecyclerView jobsRecyclerView;
    CardView profileIcon;
    SearchView searchView;
    private int currUserId;
    private User currUser;
    Context ctx;

    ImageView backBtn;

    ArrayList<Job> listedJobs = new ArrayList<>();

    JobsRecyclerViewAdapter adapter;

//    Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/montserrat_med.ttf");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        currUserId = getIntent().getIntExtra("userId", -1);

        jobsRecyclerView = findViewById(R.id.jobsRecyclerViewUser);
        searchView = findViewById(R.id.searchJobsUser);
        profileIcon = findViewById(R.id.profileIconUserDash);
        backBtn = findViewById(R.id.imageView8);

//        int searchTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView searchText = searchView.findViewById(searchTextId);
//        searchText.setTypeface(tf);

        backBtn.setOnClickListener(view -> {
            finish();
        });

        profileIcon.setOnClickListener(view -> {
            Log.d(TAG, "profile icon clicked");
            Intent moveToUserDetails = new Intent(UserDashboard.this, UserDetails.class);
            moveToUserDetails.putExtra("userId", currUser.getUserId());
            startActivity(moveToUserDetails);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        reqGetUserById(currUserId);
        reqGetJobs();
    }

    private void getData() {
        reqGetUserById(currUserId);
        reqGetJobs();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupRecyclerView() {
        adapter = new JobsRecyclerViewAdapter(ctx, listedJobs, this);
        jobsRecyclerView.setAdapter(adapter);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
    }

    protected void reqGetUserById(int userId) {
        apiServ.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d(TAG, "not successful: " + response.code());
                    return;
                }
                currUser = response.body();
                currUserId = currUser.getUserId();
                Log.d(TAG, "onResponse: " + currUser);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    protected void reqGetJobs() {
        apiServ.getAllJobs().enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d(TAG, "not successful: " + response.code());
                    return;
                }
                Log.d(TAG, "onResponse: " + response.body());
                listedJobs = (ArrayList<Job>) response.body();
                setupRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent moveToDetails = new Intent(ctx, JobDetails.class);
        moveToDetails.putExtra("jobId", listedJobs.get(position).getJobId());
        moveToDetails.putExtra("employerId", listedJobs.get(position).getEmployerId());
        moveToDetails.putExtra("userId", currUser.getUserId());
        startActivity(moveToDetails);
    }
}