package com.example.goalranger.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.goalranger.Models.Data;
import com.example.goalranger.Models.User;
import com.example.goalranger.Network.ApiInterface;
import com.example.goalranger.Network.RetrofitInstance;
import com.example.goalranger.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {


    AppCompatEditText nameView, emailView, phoneNumberView;
    String name, email, phoneNumber, name2, email2, phoneNumber2;
    private String token;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        progressDialog = new ProgressDialog(this);
        nameView = findViewById(R.id.name_profile);
        emailView = findViewById(R.id.email_profile);
        phoneNumberView = findViewById(R.id.phone_num_profile);
        name = Objects.requireNonNull(nameView.getText()).toString().trim();
        email = Objects.requireNonNull(emailView.getText()).toString().trim();
        phoneNumber = Objects.requireNonNull(phoneNumberView.getText()).toString().trim();


        Button updateProfileButton = findViewById(R.id.update_profile);
        getProfile();
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProf();

            }
        });
    }

    public void updateProf() {
        nameView = findViewById(R.id.name_profile);
        emailView = findViewById(R.id.email_profile);
        phoneNumberView = findViewById(R.id.phone_num_profile);
        name2 = Objects.requireNonNull(nameView.getText()).toString().trim();
        email2 = Objects.requireNonNull(emailView.getText()).toString().trim();
        phoneNumber2 = Objects.requireNonNull(phoneNumberView.getText()).toString().trim();


        token = getIntent().getStringExtra("token");
        progressDialog.show();
        progressDialog.setMessage("updating...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        //Defining retrofit api service
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        //Defining the user object as we need to pass it with the call
        User user = new User(name2, email2, phoneNumber2);

        //defining the call
        Call<Data> call = service.updateProf(
                token,
                user.getName(),
                user.getEmail(),
                user.getPhone_number(),
                "Team"
        );

        //calling the api
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                response.raw().body().toString();
                String getName = response.body().getData().getUser().getName();
                String getEmail = response.body().getData().getUser().getEmail();
                String getPhone = response.body().getData().getUser().getPhone_number();
                nameView.setText(getName);
                emailView.setText(getEmail);
                phoneNumberView.setText(getPhone);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("t.getMessage()------", t.getMessage());

            }
        });
    }

    public void getProfile() {
        token = getIntent().getStringExtra("token");
        progressDialog.show();
        progressDialog.setMessage("updating...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        //Defining retrofit api service

        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        //Defining the user object as we need to pass it with the cll
        Call<Data> call = service.profile(token);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                progressDialog.dismiss();

                name = response.body().getData().getUser().getName();
                email = response.body().getData().getUser().getEmail();
                phoneNumber = response.body().getData().getUser().getPhone_number();
                nameView.setText(name);
                emailView.setText(email);
                phoneNumberView.setText(phoneNumber);

                Toast.makeText(Profile.this, name, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
            }
        });
    }
}

