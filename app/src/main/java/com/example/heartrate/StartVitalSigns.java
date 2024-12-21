package com.example.heartrate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartVitalSigns extends AppCompatActivity {
    private String user;
    private int p,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_vital_signs);

        Intent extras = getIntent();
        if (extras != null) {
//            user = extras.getStringExtra("Usr");
            p = extras.getIntExtra("Page",0);
            id=extras.getIntExtra("id",0);
        }

        Button VS = this.findViewById(R.id.StartVS);

        VS.setOnClickListener(v -> {

            //switch is to decide which activity must be opened
            switch(p) {

                case 1: {
                    Intent i = new Intent(v.getContext(), HeartRateProcess.class);
//                    i.putExtra("Usr", user);
                    i.putExtra("id",id);
                    startActivity(i);
                    finish();
                }
                break;
            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(StartVitalSigns.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }


}
