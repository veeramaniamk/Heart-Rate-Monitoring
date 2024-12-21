package com.example.heartrate.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit;
    public static Retrofit getRetrofitInstance()
    {

            retrofit=new Retrofit.Builder().baseUrl("https://zoop.me/site/")
                    .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }


}
