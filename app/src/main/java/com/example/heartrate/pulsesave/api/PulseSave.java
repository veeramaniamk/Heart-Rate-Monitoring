package com.example.heartrate.pulsesave.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PulseSave {
    @FormUrlEncoded
    @POST("hr/pulse_save.php")
    Call<PulseResponse> userLogin(@Field("id")long id, @Field("pulse")int pulse,@Field("level")String level);
}
