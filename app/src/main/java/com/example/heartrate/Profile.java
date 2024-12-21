package com.example.heartrate;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heartrate.profile.api.ProfileApi;
import com.example.heartrate.profile.api.ProfileResponse;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {

    Button logout;
    public Profile(){
        // require a empty public constructor
    }
    TextView id,username,email,age,height,weight,gender;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            int  p = extras.getInt("id");
            Log.e(TAG,"onResponse "+p);

            btnSendPostRequestClicked(p);
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_profile, container, false);
        username=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        age=view.findViewById(R.id.age);
        height=view.findViewById(R.id.height);
        weight=view.findViewById(R.id.weight);
        gender=view.findViewById(R.id.gender);
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), First.class);        // Specify any activity here e.g. home or splash or login etc
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("EXIT", true);
                SharedPreferences   loginPreferences = getActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
                startActivity(i);
               getActivity().finish();

            }
        });
        return view;
    }
    private void btnSendPostRequestClicked(long id)
    {
        com.example.heartrate.profile.api.Profile profile= ProfileApi.getRetrofitInstance().create(com.example.heartrate.profile.api.Profile.class);
        Call<ProfileResponse> call =profile.userProfil(id);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if("Success".equalsIgnoreCase(response.body().getStatus()))
                {
                    username.setText(response.body().getItem().get(0).getUsername());
                    email.setText(response.body().getItem().get(0).getEmail());
                    age.setText(response.body().getItem().get(0).getAge());
                    weight.setText(response.body().getItem().get(0).getWeight());
                    gender.setText(response.body().getItem().get(0).getGender());
                    height.setText(response.body().getItem().get(0).getHeight());
                }
                else {
                    Toast.makeText(getContext()," Connection Error "+response.code(),Toast.LENGTH_LONG).show();
                    Log.e(TAG,"onResponse veeramani "+response.code());
                }
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // Log.e(TAG,"onResponse veeramani "+t.getMessage());
                Toast.makeText(getContext(),"error in profile "+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.e(TAG,"onFailure veeramani "+t.getMessage());
            }
        });
    }
}