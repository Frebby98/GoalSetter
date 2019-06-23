package com.example.goalranger.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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

public class Registration extends AppCompatActivity {

    AppCompatButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }


    public void registerUser() {
        AppCompatEditText mNameEditText, mPhoneEditText, mPasswordEditText, mConfirmPasswordEditText;
        AppCompatAutoCompleteTextView mEmailEditText;
        String name, email, phoneNumber, password, confirmPassword;

        mNameEditText = findViewById(R.id.username);
        mEmailEditText = findViewById(R.id.email_reg);
        mPhoneEditText = findViewById(R.id.phone_num);
        mPasswordEditText = findViewById(R.id.password_reg);
        mConfirmPasswordEditText = findViewById(R.id.confirm_password_reg);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();
        name = Objects.requireNonNull(mNameEditText.getText()).toString().trim();
        email = mEmailEditText.getText().toString().trim();
        phoneNumber = Objects.requireNonNull(mPhoneEditText.getText()).toString().trim();
        password = Objects.requireNonNull(mPasswordEditText.getText()).toString().trim();
        confirmPassword = Objects.requireNonNull(mConfirmPasswordEditText.getText()).toString().trim();

        if (email.isEmpty()) {
            mEmailEditText.setText("Email is required");
            mEmailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEditText.setText("Enter valid email");
            mEmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            mPasswordEditText.setText("Password is required");
            mPasswordEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            mPasswordEditText.setText("Password should be at least 6 characters long");
            mPasswordEditText.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            mNameEditText.setText("A username is required");
            mNameEditText.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()) {
            mPhoneEditText.setText("A phone number is required");
            mPhoneEditText.requestFocus();
            return;
        }

        progressDialog.setMessage("Loading......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Defining retrofit api service
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        //Defining the user object as we need to pass it with the call
        User user = new User(name, email, phoneNumber, password, confirmPassword);

        //defining the call
        Call<Data> call = service.registration(
                user.getName(),
                user.getEmail(),
                user.getPhone_number(),
                user.getPassword(),
                user.getPasswordConfirmation()
        );

        //calling the api
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if (response.code() == 422) {
                    Toast.makeText(getApplicationContext(), response.raw().body().toString(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                }
                Log.e("response", response.raw().toString());
                if (response.body() != null) {
                    Log.e("response.body---", String.valueOf(response.body().getData().getSuccess()));
                }


            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("t.getMessage()------", t.getMessage());

            }
        });
    }

}

