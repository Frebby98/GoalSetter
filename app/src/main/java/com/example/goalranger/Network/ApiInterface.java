package com.example.goalranger.Network;

import com.example.goalranger.Models.Data;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded // annotation used in POST type requests
    @POST("login")
        // API's endpoints
    Call<Data> login(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded // annotation used in POST type requests
    @POST("register")
        // API's endpoints
    Call<Data> registration(@Field("name") String name,
                            @Field("email") String email,
                            @Field("phone_number") String phone,
                            @Field("password") String password,
                            @Field("password_confirmation") String passwordConfirmation
    );

    @GET("profile")
    Call<Data> profile(@Header("Authorization") String token);
    // In registration method @Field used to set the keys and String data type is representing its a string type value and callback is used to get the response from api and it will set it in our POJO class

    @FormUrlEncoded
    @POST("goals/create")
    Call<Data> addGoal(@Header("Authorization") String token,
                       @Field("title") String title,
                       @Field("description") String description,
                       @Field("begin_date") String dueDate,
                       @Field("due_date") String endDate,
                       @Field("level") String level
    );

    @GET("goals")
    Call<Data> allGoals(@Header("Authorization") String token);

    @GET("goals/{id}")
    Call<Data> aGoal(@Header("Authorization") String token,
                     @Path("id") String id);

    @DELETE("goals/{id}/delete")
    Call<Data> deleteGoal(@Header("Authorization") String token,
                          @Path("id") int id);

    @FormUrlEncoded
    @PUT("goals/{id}/edit")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<Data> updateGoals(@Header("Authorization") String token,
                           @Path("id") String id,
                           @Field("title") String title,
                           @Field("description") String description,
                           @Field("begin_date") String dueDate,
                           @Field("due_date") String endDate,
                           @Field("level") String level);

    @FormUrlEncoded
    @PUT("profile/edit")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<Data> updateProf(@Header("Authorization") String token,
                          @Field("name") String name,
                          @Field("email") String email,
                          @Field("phone_number") String phone,
                          @Field("account_type") String accountType

    );

    @FormUrlEncoded
    @POST("goals/{id}/tasks/create")
    Call<Data> createTask(@Header("Authorization") String token,
                          @Path("id") String id,
                          @Field("task_title") String title,
                          @Field("description") String description,
                          @Field("begin_date") String beginDate,
                          @Field("due_date") String dueDate
    );

    @GET("goals/{id}/tasks")
    Call<Data> allTasks(@Header("Authorization") String token,
                        @Path("id") String id);


    @GET("goals/{id}/tasks/{id}")
    Call<Data> aTask(@Header("Authorization") String token,
                     @Path("goal_id") String goal_id,
                     @Path("id") String id);


    @FormUrlEncoded
    @PUT("goals/{id}/tasks/{id}/edit")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<Data> updateTask(@Header("Authorization") String token,
                          @Path("goal_id") String goal_id,
                          @Path("id") String id,
                          @Field("task_title") String title,
                          @Field("description") String description,
                          @Field("begin_date") String beginDate,
                          @Field("due_date") String dueDate);


    @DELETE("goals/{id}/tasks/{id}/delete")
    Call<Data> deleteTask(@Header("Authorization") String token,
                          @Path("goal_id") String goal_id,
                          @Path("id") int id);

}
