package com.ethiofintech.ethioexpensetracker.network;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/auth/login")
    Call<Map<String, Object>> login(@Body Map<String, String> loginRequest);

    @POST("api/auth/register")
    Call<Void> register(@Body Map<String, String> registerRequest);
    // Add this inside the ApiService interface
    @POST("api/transactions/add")
    Call<Void> addTransaction(@Body Map<String, Object> transactionData);

    @GET("api/transactions/user/{userId}")
    Call<List<Map<String, Object>>> getTransactions(@Path("userId") Long userId);
}
