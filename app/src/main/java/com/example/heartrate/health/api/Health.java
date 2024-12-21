package com.example.heartrate.health.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Health {

        @FormUrlEncoded
        @POST("hr/health_details.php")
        Call<HealthResponse> userHealth(@Field("id")long id, @Field("gender")String gender,
                                        @Field("age")int age, @Field("height")int height,@Field("weight")int weight);
    }
