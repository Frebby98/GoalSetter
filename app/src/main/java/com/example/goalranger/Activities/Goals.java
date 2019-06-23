package com.example.goalranger.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.goalranger.Adapter.GoalAdapter;
import com.example.goalranger.Models.Data;
import com.example.goalranger.Models.Goal;
import com.example.goalranger.Network.ApiInterface;
import com.example.goalranger.Network.RetrofitInstance;
import com.example.goalranger.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Goals extends AppCompatActivity {
    FloatingActionButton fab;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private GoalAdapter adapter;
    private String token, token2;
    private ArrayList<Goal> datalist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        token = getIntent().getStringExtra("token");
        token2 = getIntent().getStringExtra("token2");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle("Goals");
        toolbar.inflateMenu(R.menu.goal_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.profile) {
                    Intent intent = new Intent(Goals.this, Profile.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.refresh) {
                    startAPiService();
                }
                return true;
            }

        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading......");
        progressDialog.show();
        startAPiService();
        /*Call the method with parameter in the interface to get the employee data*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAPiService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startAPiService();
    }

    private void startAPiService() {
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        recyclerView = findViewById(R.id.goal_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Goals.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        getList(service);
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Goals.this, AddGoals.class);
                intent.putExtra("fabToken", token);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Goal> getList(ApiInterface service) {
        Call<Data> call;
        if (token != null) {
            call = service.allGoals(token);
        } else {
            service.allGoals(token2);
            call = service.allGoals(token2);
        }
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                progressDialog.dismiss();
                Goal goal = new Goal();
                Log.e("response--------", response.raw().body().toString());
                if (datalist != null) {
                    datalist = response.body().getData().getGoalArrayList();
                    adapter = new GoalAdapter(datalist, Goals.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.notifyItemRemoved(goal.getId());
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Goals.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return datalist;
    }
}