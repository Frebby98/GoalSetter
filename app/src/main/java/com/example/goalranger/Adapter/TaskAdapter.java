package com.example.goalranger.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goalranger.Models.Data;
import com.example.goalranger.Models.Task;
import com.example.goalranger.Network.ApiInterface;
import com.example.goalranger.Network.RetrofitInstance;
import com.example.goalranger.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private static final String MyPREFERENCES = "GoalStack";
    Context context;
    private ArrayList<Task> taskArrayList;
    private SharedPreferences preference;

    public TaskAdapter(ArrayList<Task> taskArrayList, Context context) {
        this.taskArrayList = taskArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_goal, viewGroup, false);
        preference = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
        final Task model = taskArrayList.get(i);
        taskHolder.setTitle(model.getTask_title());
        taskHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String token = preference.getString("token", "");

                int taskId = model.getId();
                String goalId = model.getGoal_id();
                ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);


                Call<Data> call = service.aTask(
                        token,
                        goalId,
                        String.valueOf(taskId)
                );
                call.enqueue(new Callback<Data>() {

                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArrayList == null ? 0 : taskArrayList.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        View mView;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            taskTitle = mView.findViewById(R.id.task_title);
        }

        public void setTitle(String titles) {
            taskTitle.setText(titles);
        }
    }
}
