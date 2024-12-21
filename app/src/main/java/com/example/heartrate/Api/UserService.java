package com.example.heartrate.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("hr/login.php")
    Call<LoginResponse> userLogin(@Field("username")String username,@Field("password")String password);
}
