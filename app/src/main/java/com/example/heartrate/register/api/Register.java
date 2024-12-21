package com.example.heartrate.register.api;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Register {
    @FormUrlEncoded
    @POST("hr/signup.php")
    Call<RegisterResponse> userRegister(@Field("username")String username,@Field("email")String email,
                                        @Field("password")String password,@Field("confirm_password")String confirm_password);
}
