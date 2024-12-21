package com.example.heartrate;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heartrate.image.radius.ImageConverter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentTransaction;
public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    ImageView iv1, iv2;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.measure);
//        iv1 = findViewById(R.id.StartVS);
////        iv2 = (ImageView)findViewById(R.id.imageView2);
//
//        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.finger_on_your_camera_2);
//        Bitmap circularBitmap1 = ImageConverter.getRoundedCornerBitmap(bitmap1, 100);
////        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.img2);
////        Bitmap circularBitmap2 = ImageConverter.getRoundedCornerBitmap(bitmap2, 100);
//
//        iv1.setImageBitmap(circularBitmap1);
////        iv2.setImageBitmap(circularBitmap2);

    }
    public void end()
    {
        this.finish();
    }

    Measure measure = new Measure();
    Profile profile = new Profile();
    Health health = new Health();
    private String user;
    private int id;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("usr");
            id=extras.getInt("id");

            //The key argument here must match that used in the other activity
        }
        if(item.getItemId()==R.id.measure){
            Bundle mBundle = new Bundle();
            mBundle.putString("usr",user);
            mBundle.putInt("id",id);
            measure.setArguments(mBundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, measure)
                    .commit();
            return true;
        }
        else if(item.getItemId()==R.id.Health){
            Bundle mBundle = new Bundle();
            mBundle.putString("usr",user);
            mBundle.putInt("id",id);
            health.setArguments(mBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, health).commit();
            return true;
        }else if(item.getItemId()==R.id.Profile){
            Bundle mBundle = new Bundle();
            mBundle.putInt("id",id);
            profile.setArguments(mBundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profile)
                    .commit();

            return true;
        }
        else return false;
    }
}