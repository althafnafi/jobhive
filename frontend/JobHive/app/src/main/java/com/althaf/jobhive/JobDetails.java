package com.althaf.jobhive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.althaf.jobhive.model.AppStatus;
import com.althaf.jobhive.model.Job;
import com.althaf.jobhive.model.UserApplication;
import com.althaf.jobhive.request.ApiUtils;
import com.althaf.jobhive.request.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetails extends AppCompatActivity {

    BaseApiService apiServ;
    Context ctx;
    ImageView editJobImg;
    CardView editJobBtn, getListBtn, statusCard;
    Job currJob;
    private int currUserId = -1;
    private int currJobId = -1;
    TextView jobTitleTv, companyNameTv, jobDescTv, jobReqTv, bottomBtnTv,
            cityLocationTv, jobCatTv, avgSalaryTv, lastUpdatedTv, statusTv;
    // For user
    EditText messageBox;
    TextView messageBoxTitle;

    UserApplication currUserApplication;

    AppStatus currAppStatus;
    Button bottomBtn;
    Boolean isBookmarked = false;
//    Dialog popupApply;
//    ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        editJobBtn = findViewById(R.id.editJobButtonDetails);
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
        editJobImg = findViewById(R.id.imageEditJobBtn);
        getListBtn = findViewById(R.id.getListCard);
        messageBoxTitle = findViewById(R.id.msgTitleDetails);
        messageBox = findViewById(R.id.textMessage);
        statusTv = findViewById(R.id.statusTextDetaiils);
        statusCard = findViewById(R.id.statusCard);

        currJobId = getIntent().getIntExtra("jobId", -1);
        currUserId = getIntent().getIntExtra("userId", -1);

        reqGetJobById(currJobId);
        if (!isUser()) {
//            getListBtn.setVisibility(CardView.VISIBLE);
            messageBox.setVisibility(View.INVISIBLE);
            messageBoxTitle.setVisibility(View.INVISIBLE);

            editJobImg.setImageResource(R.drawable.pencil_icon);

            editJobBtn.setOnClickListener(view -> {
                // Move to edit job activity
                Log.d(getString(R.string.log_str), "editJobBtn clicked");
            });

            getListBtn.setOnClickListener(view -> {
                // Move to list of applicants activity
                Log.d(getString(R.string.log_str), "getListBtn clicked");
            });

            bottomBtnTv.setText("SEE APPLICANTS");
        } else {
//            getListBtn.setVisibility(CardView.INVISIBLE);
            messageBox.setVisibility(View.VISIBLE);
            messageBoxTitle.setVisibility(View.VISIBLE);
            editJobImg.setImageResource(R.drawable.bookmark_empty);
            editJobBtn.setOnClickListener(view -> {
                // Move to edit job activity
                Log.d(getString(R.string.log_str), "bookmark Clicked!");
                reqToggleBookmark(currUserId, currJobId);
            });

            reqGetUserApp(currUserId, currJobId);
        }

        bottomBtn.setOnClickListener(view -> {
            if (isUser()) {
                applyJob(currJobId, currUserId, messageBox.getText().toString());
            }
            else {
                Log.d(getString(R.string.log_str), "bottomBtn clicked");
//                Intent moveToSeeApplicants = new Intent(ctx, SeeApplicants.class);
//                moveToSeeApplicants.putExtra("jobId", currJobId);
//                startActivity(moveToSeeApplicants);
            }
        });


    }

    public void showPopupApply() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_applied, null);


        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popup = new PopupWindow(popupView, width, height, focusable);
        popup.showAtLocation(new ConstraintLayout(this), Gravity.CENTER, 0, 0);

//        return;
//        ImageView closeBtn;
//        popupApply.setContentView(R.layout.popup_applied);
//        closeBtn = findViewById(R.id.closeBtnPopup);
//        closeBtn.setOnClickListener(view -> {
//            popupApply.dismiss();
//        });
//        popupApply.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLU));
//        popupApply.show();
    }

//    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
//        Drawable dim = new ColorDrawable(Color.BLACK);
//        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
//        dim.setAlpha((int) (255 * dimAmount));
//
//        ViewGroupOverlay overlay = parent.getOverlay();
//        overlay.add(dim);
//    }
//
//    public static void clearDim(@NonNull ViewGroup parent) {
//        ViewGroupOverlay overlay = parent.getOverlay();
//        overlay.clear();
//    }

    protected void reqGetUserApp(int currUserId, int currJobId) {
        apiServ.getAppByUserId(currUserId, currJobId).enqueue(new Callback<UserApplication>() {
            @Override
            public void onResponse(Call<UserApplication> call, Response<UserApplication> response) {
                if (!response.isSuccessful()) {
                    Log.d(getString(R.string.log_str), "error: " + response.code());
                    return;
                }
                currUserApplication = response.body();
                Log.d(getString(R.string.log_str), "currUserApplication: " + currUserApplication.toString());
                // update status bar
                statusTv.setText(currUserApplication.getStatus());
                statusCard.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<UserApplication> call, Throwable t) {
                Log.d(getString(R.string.log_str), "error: " + t.getMessage());
            }
        });
    }

    protected boolean isUser() {
        return currUserId != -1;
    }

    private void localToggleBookmark() {
        if (isBookmarked) {
            editJobImg.setImageResource(R.drawable.bookmark_empty);
            isBookmarked = false;
        }
        else {
            editJobImg.setImageResource(R.drawable.bookmark_fill);
            isBookmarked = true;
        }
    }



    //
    protected void reqToggleBookmark(int userId, int jobId) {
        localToggleBookmark();
        return;
    }

    protected void applyJob(int jobId, int userId, String message) {
        if (jobId == -1 || userId == -1 || message == null) {
            Log.d(getString(R.string.log_str), "error: " + "jobId or userId is -1");
            Toast.makeText(ctx, "You can't apply for this job", Toast.LENGTH_LONG).show();
            return;
        }
        if (message == "") message = "No message given";

        UserApplication newJobApp = new UserApplication(jobId, userId, message);

        apiServ.applyJob(newJobApp).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d(getString(R.string.log_str), "failed: " + response.code());
                    return;
                }
                Job respJob = response.body();
                Log.d(getString(R.string.log_str), "respJob: " + respJob);
                currJob = respJob;
                showPopupApply();
                messageBox.setText("");
                messageBox.clearFocus(); ;
//                applyDim(root, 0.5f);
                reqGetUserApp(currUserId, currJobId);
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Log.d(getString(R.string.log_str), "onFailure: " + t.getMessage());
            }
        });
    }

    protected void reqGetJobById(int jobId) {
        apiServ.getJobById(jobId).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d(getString(R.string.log_str), "failed: " + response.code());
                    return;
                }
                Job respJob = response.body();
                Log.d(getString(R.string.log_str), "respJob: " + respJob);
                currJob = respJob;
                updateVal();
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Log.d(getString(R.string.log_str), "onFailure: " + t.getMessage());
            }
        });
    }

    protected void reqDeleteJobById(int jobId) {
        apiServ.deleteJob(jobId).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d(getString(R.string.log_str), "failed: " + response.code());
                    return;
                }
                Job respJob = response.body();
                Log.d(getString(R.string.log_str), "respJob: " + respJob);
                currJob = respJob;
//                Intent intent = new Intent(JobDetails.this, EmployerDashboard.class);
//                int employerId = getIntent().getIntExtra("employerId", -1);
//                Log.d(getString(R.string.log_str), "val:" + String.valueOf(employerId));
//                intent.putExtra("employerId", intent.getIntExtra("employerId", -1));
//                startActivity(intent);

                Toast.makeText(ctx, "Job deleted successfully", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Log.d(getString(R.string.log_str), "onFailure: " + t.getMessage());
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
        lastUpdatedTv.setText("Posted " + currJob.getCreatedAtDiff() + " day(s) ago");
    }


}