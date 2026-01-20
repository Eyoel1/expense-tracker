package com.ethiofintech.birrwise.network;

import com.google.gson.JsonObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // 1. Auth
    @POST("/api/auth/login")
    Call<JsonObject> loginUser(@Body JsonObject loginData);

    @POST("/api/auth/register")
    Call<JsonObject> registerUser(@Body JsonObject userData);

    @PUT("/api/auth/update/{userId}")
    Call<JsonObject> updateProfile(@Path("userId") String userId, @Body JsonObject updateData);

    // 2. Expenses
    @POST("/api/expenses/add")
    Call<JsonObject> addExpense(@Body JsonObject expenseData);

    // 3. Analytics & Summary
    @GET("/api/expenses/summary/{userId}")
    Call<JsonObject> getSummary(@Path("userId") String userId);

    @GET("/api/expenses/user/{userId}")
    Call<List<JsonObject>> getAllExpenses(@Path("userId") String userId);
}