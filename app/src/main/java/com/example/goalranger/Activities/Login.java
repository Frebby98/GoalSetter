package com.example.goalranger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    private static final String MyPREFERENCES = "GoalGetter";
    AppCompatButton mLoginButton;
    SharedPreferences preference;
    AppCompatButton mCreateAccount;
    private AutoCompleteTextView mEmailEditText;
    private EditText mPasswordEditText;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mEmailEditText = findViewById(R.id.email_log);
        mPasswordEditText = findViewById(R.id.password_log);
        mCreateAccount = findViewById(R.id.create_account);
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }


    public void loginUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        email = mEmailEditText.getText().toString().trim();
        password = Objects.requireNonNull(mPasswordEditText.getText()).toString().trim();

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

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Defining retrofit api service
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        //Defining the user object as we need to pass it with the call
        User user = new User(email, password);

        //defining the call
        Call<Data> call = service.login(
                user.getEmail(),
                user.getPassword()
        );

        //calling the api
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                Log.e("response", response.raw().toString());
                Data data = response.body();

                if (response.code() == 401) {
                    Toast.makeText(getApplicationContext(), "Invalid validation", Toast.LENGTH_LONG).show();
                }

                //displaying the message from the response as toast
                if (response.body() != null) {
//                    Toast.makeText(getApplicationContext(), String.valueOf(response.body().getData().getSuccess()), Toast.LENGTH_LONG).show();
                    Log.e("response.body---", String.valueOf(response.body().getSuccess()));
                    if (data != null) {
                        String token = data.getData().getToken();
                        if (token.isEmpty()) {
                            Toast.makeText(Login.this, "no token found", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences.Editor editor = preference.edit();
                            editor.putString("token", token);
                            editor.apply();

                            Intent intent = new Intent(Login.this, Goals.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                        }
                    }
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
