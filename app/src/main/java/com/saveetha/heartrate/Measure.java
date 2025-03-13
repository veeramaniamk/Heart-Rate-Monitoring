package com.saveetha.heartrate;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class Measure extends Fragment {

    public Measure(){
        // require a empty public constructor
    }
    private Dialog dialog;
    private String user;
    private int p;
    private int id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measure, container, false);
        ImageView HeartRate = view.findViewById(R.id.imageView);

        Bundle extras = getArguments();
        if (extras != null) {
//            user = extras.getString("usr");
            id = extras.getInt("id");

            //The key argument here must match that used in the other activity
        }

        //Every Test Button sends the username + the test number, to go to the wanted test after the instructions activity
        HeartRate.setOnClickListener(v -> {
            p = 1;

            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.activity_start_vital_signs);
            dialog.show();
            dialog.findViewById(R.id.StartVS).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), HeartRateProcess.class);
                    i.putExtra("Page", p);
//            i.putExtra("Usr",user);
                    i.putExtra("id", id);
                    startActivity(i);
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        });


        // Inflate the layout for this fragment
        return view;
    }
}