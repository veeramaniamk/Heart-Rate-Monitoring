package com.example.heartrate.profile.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Profile {
    @FormUrlEncoded
    @POST("hr/profile.php")
    Call<ProfileResponse> userProfil(@Field("id")long id);
}
