package com.saveetha.heartrate.graphview;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface graph {
    @FormUrlEncoded
    @POST("hr/pulse_details.php")
    Call<GraphResponse> userGraph(@Field("id")long id,@Field("date")String date);
}
