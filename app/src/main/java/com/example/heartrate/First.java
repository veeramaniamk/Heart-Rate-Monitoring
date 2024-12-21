package com.example.heartrate;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heartrate.Api.ApiClient;
import com.example.heartrate.Api.LoginResponse;
import com.example.heartrate.Api.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class First extends AppCompatActivity {
    public Button Meas;
    public TextView acc;
    public EditText ed1, ed2;
    private Toast mainToast;
    public static String passStr, usrStr, checkpassStr, usrStrlow;
//    UserDB check = new UserDB(this);
    CheckBox chkRememberMe;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Meas = findViewById(R.id.prime);
        acc = findViewById(R.id.newacc);
        ed1 = findViewById(R.id.edtu1);
        ed2 = findViewById(R.id.edtp1);
        chkRememberMe = findViewById(R.id.checkBoxRemember);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin) {
            ed1.setText(loginPreferences.getString("username", ""));
            ed2.setText(loginPreferences.getString("password", ""));
            chkRememberMe.setChecked(true);
        }

        Meas.setOnClickListener(v -> {
            usrStrlow = ed1.getText().toString();
            passStr = ed2.getText().toString();
            usrStr = usrStrlow.toLowerCase();

            if (usrStr.length() < 3 || usrStr.length() > 20) {
                mainToast = Toast.makeText(getApplicationContext(), "Username length must be between 3-20 characters", Toast.LENGTH_SHORT);
                mainToast.show();
            }

            if (passStr.length() < 3 || passStr.length() > 20) {
                mainToast = Toast.makeText(getApplicationContext(), "Password length must be between 3-20 characters", Toast.LENGTH_SHORT);
                mainToast.show();
            } else if (passStr.isEmpty() || usrStr.isEmpty()) {
                mainToast = Toast.makeText(getApplicationContext(), "Please enter your Username and Password ", Toast.LENGTH_SHORT);
                mainToast.show();
            } else {

                    if (chkRememberMe.isChecked()){
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", usrStr);
                        loginPrefsEditor.putString("password", passStr);
                        loginPrefsEditor.apply();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                btnSendPostRequestClicked(usrStrlow,passStr);

            }

        });

        acc.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), Login.class);
            startActivity(i);
        });
    }
        private void btnSendPostRequestClicked(String username,String password)
        {
            UserService userService= ApiClient.getRetrofitInstance().create(UserService.class);
            Call<LoginResponse> call =userService.userLogin(username,password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if("Success".equalsIgnoreCase(response.body().getStatus()))
                    {
//                        Toast.makeText(First.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        Intent i = new Intent(First.this, MainActivity.class);
//                        i.putExtra("usr ", " "+usrStr);
                        i.putExtra("id",response.body().getUser_id());
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(First.this,"user name or password are incorrect "+response.code(),Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                   // Log.e(TAG,"onResponse veeramani "+t.getMessage());
                    Toast.makeText(First.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }


}
