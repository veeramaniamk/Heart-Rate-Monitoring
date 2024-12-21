package com.example.heartrate.pulsesave.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PulseApiClient {
    public static Retrofit retrofit;
    public static Retrofit getRetrofitInstance()
    {

        retrofit=new Retrofit.Builder().baseUrl("https://zoop.me/site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
