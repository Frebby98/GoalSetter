package com.example.goalranger.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goalranger.Activities.AddGoals;
import com.example.goalranger.Models.Data;
import com.example.goalranger.Models.Goal;
import com.example.goalranger.Network.ApiInterface;
import com.example.goalranger.Network.RetrofitInstance;
import com.example.goalranger.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.DataHolder> {
    private static final String MyPREFERENCES = "GoalStack";
    Context context;
    SharedPreferences preference;
    private ArrayList<Goal> dataList;

    public GoalAdapter(ArrayList<Goal> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_goal, viewGroup, false);
        preference = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataHolder dataHolder, int i) {
        final Goal model = dataList.get(i);
//        Toast.makeText(context,model.getTitle(), Toast.LENGTH_SHORT).show();
        dataHolder.setTitle(model.getTitle());
        dataHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "long clicked", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete or Edit Goal?");
                final String token = preference.getString("token", "");
                // set dialog message
                alertDialog
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int id) {

                                id = model.getId();
                                ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

                                Call<Data> call = service.deleteGoal(
                                        token,
                                        id
                                );
                                call.enqueue(new Callback<Data>() {

                                    @Override
                                    public void onResponse(Call<Data> call, Response<Data> response) {
                                        //  Toast.makeText(context,response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                                        dataList.remove(model.getId() - 1);
                                        notifyDataSetChanged();

                                        notifyItemRemoved(model.getId());


                                    }

                                    @Override
                                    public void onFailure(Call<Data> call, Throwable t) {
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        })
                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                id = model.getId();

                                final Intent intent = new Intent(context, AddGoals.class);
                                intent.putExtra("editToken", token);
                                intent.putExtra("id", id);
                                context.startActivity(intent);
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog1Create = alertDialog.create();

                // show it
                alertDialog1Create.show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {
        TextView title;
        View mView;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            title = mView.findViewById(R.id.goals_title);
        }

        public void setTitle(String titles) {
            title.setText(titles);
        }

    }
}
