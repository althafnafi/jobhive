package althaf.jobhive_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import althaf.jobhive_android.model.User;
import althaf.jobhive_android.request.ApiUtils;
import althaf.jobhive_android.request.BaseApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    BaseApiService apiServ;
    EditText emailBox, passBox;
    Button confirmRegBtn;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiServ = ApiUtils.getApiService();
        ctx = this;

        // Define components
        emailBox = findViewById(R.id.emailBoxReg);
        passBox = findViewById(R.id.passBoxReg);
        confirmRegBtn = findViewById(R.id.registerBtnReg);

        confirmRegBtn.setOnClickListener(view -> {
            // Call API to register
            requestRegister();
        });
    }


    protected void requestRegister() {
        String inp_email = emailBox.getText().toString();
        String inp_pass = passBox.getText().toString();
        apiServ.registerUser(
                inp_email,
                inp_pass
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ctx, "Successfully registered a new account", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, "Something weird happened?", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ctx,"Registration failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}