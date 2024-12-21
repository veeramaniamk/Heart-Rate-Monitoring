package com.example.heartrate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.heartrate.health.api.HealthApiClient;
import com.example.heartrate.health.api.HealthResponse;
import com.example.heartrate.register.api.Register;
import com.example.heartrate.register.api.RegisterApiClient;
import com.example.heartrate.register.api.RegisterResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Button Log; // Change type to Button
    public EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8;
    private Toast mainToast;
    public Spinner GenderSpin;
    public String m1 = "Male";
    public String m2 = "Female";
    String text;
    public String nameStr, weightStr, heightStr, ageStr, passStr, usrStr, usrStrlow, passConStr, emailStr;
    private int age, weight, height;

    int c, y = 0;
    int check1 = 0;

    //Camera Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    //Password Pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Checking for camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        Log = findViewById(R.id.register); // Change casting to Button
        ed1 = findViewById(R.id.height);
        ed2 = findViewById(R.id.weight);
        ed3 = findViewById(R.id.name);
        ed4 = findViewById(R.id.age);
        ed5 = findViewById(R.id.username);
        ed6 = findViewById(R.id.password);
        ed7 = findViewById(R.id.re_password);
        ed8 = findViewById(R.id.email);
        GenderSpin = findViewById(R.id.SGender);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        GenderSpin.setAdapter(adapter);


        Log.setOnClickListener(v -> {

            check1 = 0;
            heightStr = ed1.getText().toString();
            weightStr = ed2.getText().toString();
            nameStr = ed3.getText().toString();
            ageStr = ed4.getText().toString();
            usrStrlow = ed5.getText().toString();
            passStr = ed6.getText().toString();
            passConStr = ed7.getText().toString();
            emailStr = ed8.getText().toString();
            usrStr = usrStrlow.toLowerCase();


            //Email Validation
            String emailInput = emailStr;
            if (emailInput.isEmpty()) {
                check1 = 1;
                ed8.setError("Field can't be empty");
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    check1 = 1;
                    ed8.setError("Please enter a valid email address");
                } else {
                    check1 = 0;
                    ed8.setError(null);
                }
            }

            //Username Validation
            if (usrStr.isEmpty()) {
                ed5.setError("Field can't be empty");
                check1 = 1;
            } else if (usrStr.length() > 15) {
                ed5.setError("Username too long");
                check1 = 1;
            } else {
                ed5.setError(null);
                check1 = 0;
            }

            //Password Validation
            if (passStr.isEmpty()) {
                check1 = 1;
                ed6.setError("Field can't be empty");
            } else if (!PASSWORD_PATTERN.matcher(passStr).matches()) {
                check1 = 1;
                ed6.setError("Password too weak: must contain\nUppercase characters (A-Z)\n" +
                        "Lowercase characters (a-z)\n" +
                        "Digits (0-9)\n" +
                        "Special characters (~!@#$%&*_:;'.?/)");
            } else {
                check1 = 0;
                ed6.setError(null);
            }
            if (!(passStr.equals(passConStr))) {
                check1 = 1;
                mainToast = Toast.makeText(getApplicationContext(), "Password don't match !", Toast.LENGTH_SHORT);
                mainToast.show();
            }

            //Checking other Inputs
            if (ageStr.isEmpty() || nameStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || passStr.isEmpty() || passConStr.isEmpty() || emailStr.isEmpty() || usrStr.isEmpty()) {
                check1 = 1;
                mainToast = Toast.makeText(getApplicationContext(), "Please fill all your data ", Toast.LENGTH_SHORT);
                mainToast.show();
            } else if (check1 == 0) {

                check1 = 0;
                age = Integer.parseInt(ageStr);
                weight = Integer.parseInt(weightStr);
                height = Integer.parseInt(heightStr);
                text = GenderSpin.getSelectedItem().toString();
                int k = 0;
                if (text.equals(m1)) //If gender is male K = 1
                    k = 1;
                if (text.equals(m2)) //If gender is female K = 2
                    k = 2;

                btnSendPostRequestClicked(usrStrlow,emailStr,passStr,passConStr);

            }
        });
    }
    private void healthDetails(long id,String gender,int age,int height,int weight)
    {
        com.example.heartrate.health.api.Health healthService= HealthApiClient.getRetrofitInstance().create(com.example.heartrate.health.api.Health.class);
        Call<HealthResponse> call =healthService.userHealth(id,gender,age,height,weight);
        call.enqueue(new Callback<HealthResponse>() {
            @Override
            public void onResponse(Call<HealthResponse> call, Response<HealthResponse> response) {
                //Log.e(TAG,"onResponse "+response.code());

                //Log.e(TAG,"onResponse veeramani "+response.body().getUser_id());
                if("Success".equalsIgnoreCase(response.body().getStatus()))
                {
                    //Toast.makeText(Login.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Login.this, First.class);
                    mainToast = Toast.makeText(getApplicationContext(), " "+response.body().getMessage(), Toast.LENGTH_SHORT);
                    mainToast.show();
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(Login.this," "+response.body().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<HealthResponse> call, Throwable t) {
                // Log.e(TAG,"onResponse veeramani "+t.getMessage());
                Toast.makeText(Login.this,t.getMessage(),Toast.LENGTH_LONG);

            }
        });

    }

    private void btnSendPostRequestClicked(String username,String email,String password,String confirm_password)
    {
        Register userService= RegisterApiClient.getRetrofitInstance().create(Register.class);
        Call<RegisterResponse> call =userService.userRegister(username,email,password,confirm_password);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if("Success".equalsIgnoreCase(response.body().getStatus()))
                {
                    healthDetails(response.body().getUser_id(),text,Integer.parseInt(ageStr),Integer.parseInt(heightStr),Integer.parseInt(weightStr));

                }
                else {
                    Toast.makeText(Login.this," "+response.body().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Login.this,t.getMessage(),Toast.LENGTH_LONG);

            }
        });
    }
}
