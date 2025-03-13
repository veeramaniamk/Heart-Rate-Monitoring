package com.saveetha.heartrate.register.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterApiClient {

    public static Retrofit retrofit;
    public static Retrofit getRetrofitInstance()
    {

        retrofit=new Retrofit.Builder().baseUrl("https://zoop.me/site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
