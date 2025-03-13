package com.saveetha.heartrate;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.saveetha.heartrate.Api.ApiClient;
import com.saveetha.heartrate.Api.LoginResponse;
import com.saveetha.heartrate.Api.UserService;

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

        checkAppUpdate(this);
    }

    private void checkAppUpdate(Context context) {

        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // handle callback
                        if (result.getResultCode() != RESULT_OK) {
                            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
                            // If the update is canceled or fails,
                            // you can request to start the update again.
                        } else {
                            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // Request the update.
                Toast.makeText(context, "Update Available", Toast.LENGTH_SHORT).show();
                appUpdateManager.startUpdateFlowForResult(
                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,
                        // an activity result launcher registered via registerForActivityResult
                        activityResultLauncher,
                        // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                        // flexible updates.
                        AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build());
            } else {
                Toast.makeText(context, "No Update Available", Toast.LENGTH_SHORT).show();
            }
        });
        appUpdateInfoTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "update Exception", Toast.LENGTH_SHORT).show();
            }
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
