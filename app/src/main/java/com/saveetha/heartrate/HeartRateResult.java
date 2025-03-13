package com.saveetha.heartrate;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.heartrate.pulsesave.api.PulseApiClient;
import com.saveetha.heartrate.pulsesave.api.PulseResponse;
import com.saveetha.heartrate.pulsesave.api.PulseSave;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeartRateResult extends AppCompatActivity {

    private String user, Date;
    int HR;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    private long id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_result);
        String level=null;
        Date = df.format(today);
        TextView RHR = this.findViewById(R.id.HRR);
        TextView levelText=findViewById(R.id.heartScoreValue);
        Button next=findViewById(R.id.nextButton);
        Bundle bundle = getIntent().getExtras();
        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finish();

            }
        });
        if (bundle != null) {
            HR = bundle.getInt("bpm");
//            user = bundle.getString("Usr");
//            Log.d("DEBUG_TAG", "ccccc" + user);
            RHR.setText(String.valueOf(HR)+" bpm");
            id=bundle.getLong("id");
            if(HR>60&&90>HR) level="GOOD";
            else if(90<HR &&200>HR) level="DANGER";
            else if(40<HR&&60>HR) level="NORMAL";
            else if(40>HR) level="LOW";
            levelText.setText(level);
        }
//        Toast.makeText(HeartRateResult.this,"my "+String.valueOf(HR)+" toast",Toast.LENGTH_LONG).show();
        btnSendPostRequestClicked(id,HR,level);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(HeartRateResult.this,MainActivity.class);
//                intent.putExtra("id",id);
//                startActivity(intent);
//                finish();
//                closeContextMenu();
//
//            }
//        });
    }
    private void finishAgain(){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(HeartRateResult.this, MainActivity.class);
//        i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }
    private void btnSendPostRequestClicked(long id,int pulse,String level)
    {
        PulseSave userService= PulseApiClient.getRetrofitInstance().create(PulseSave.class);
        Call<PulseResponse> call = userService.userLogin(id,pulse,level);
        call.enqueue(new Callback<PulseResponse>() {
            @Override
            public void onResponse(Call<PulseResponse> call, Response<PulseResponse> response) {
                //Log.e(TAG,"onResponse veeramani "+response.body().getUser_id());
                if("Success".equalsIgnoreCase(response.body().getStatus()))
                {
                    Toast.makeText(HeartRateResult.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        Log.e(TAG,"heart rate result"+response.body().getMessage()+" id "+id);
                }
                else {
                    Toast.makeText(HeartRateResult.this,"connection error "+response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<PulseResponse> call, Throwable t) {
                // Log.e(TAG,"onResponse veeramani "+t.getMessage());
                Toast.makeText(HeartRateResult.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
