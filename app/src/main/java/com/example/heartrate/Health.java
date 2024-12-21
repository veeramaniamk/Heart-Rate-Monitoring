package com.example.heartrate;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.heartrate.graphview.GraphResponse;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Health extends Fragment {

    public Health(){
        // require a empty public constructor
    }
    CalendarView calendar;
    TextView date_view;
    long id;
    View view;
    GraphView graph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_health, container, false);
        // By ID we can use each component
        // which id is assign in xml file
        // use findViewById() to get the
        // CalendarView and TextView
        Bundle extras = getArguments();
        if (extras != null) {
//            user = extras.getString("usr");
            id=extras.getInt("id");

        }
        calendar = (CalendarView)view.findViewById(R.id.calendar);
        date_view = (TextView)view.findViewById(R.id.date_view);

        // Add Listener in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view,int year,int month,int dayOfMonth)
                    {

                        // Store the value of date with
                        // format in String type Variable
                        // Add 1 in month because month
                        // index is start with 0
                         Date = year+ "-" + (month + 1) + "-" +dayOfMonth  ;

                        // set this date in TextView for Display
//                        date_view.setText(Date);
                        Log.e(TAG,"from health in calender "+Date+" id "+id)    ;
                        btnSendPostRequestClicked(id,Date);
                        if(graph!=null)
                        graph.removeAllSeries();

                    }
        });
        return view;
    }
    void graph(String[] pulseString){
         graph = view.findViewById(R.id.graphview);
        int[] pulse=new int[pulseString.length];
        for(int i=0;i<pulseString.length;i++) pulse[i]=Integer.parseInt(pulseString[i]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int i=0;i<pulse.length;i++) series.appendData(new DataPoint(i,pulse[i]),true,pulse.length);
        graph.addSeries(series);

    }
    String Date;
    private void btnSendPostRequestClicked(long id,String date)
    {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://zoop.me/site/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        com.example.heartrate.graphview.graph userGrap= retrofit.create(com.example.heartrate.graphview.graph.class);
        Call<GraphResponse> call =userGrap.userGraph(id,date);
        call.enqueue(new Callback<GraphResponse>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {

                if("Success".equalsIgnoreCase(response.body().getStatus()))
                {

                    for(int i=0;i<response.body().getDetails().size();i++)
                        Log.e(TAG,"pulse from health "+i+" "+response.body().getDetails().get(i).getPulse());

                   graph(response.body().getPulses());
                }
                else {
                    Toast.makeText(getContext(),"No Available Data For This Date",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                // Log.e(TAG,"onResponse veeramani "+t.getMessage());
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}