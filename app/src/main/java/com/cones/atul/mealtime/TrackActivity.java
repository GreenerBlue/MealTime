package com.cones.atul.mealtime;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;

public class TrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        Intent track = getIntent();
        //Date beginTime = new Date(track.getStringExtra("orderTime"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(track.getLongExtra("orderTime",15000));
        TextView trackText = findViewById(R.id.track_time_text);
        trackText.setText(calendar.getTime().toString().substring(11,19));

        TextView order = findViewById(R.id.track_order_text);
        order.setText(Arrays.toString(track.getStringArrayExtra("orderDetails")));

        final TextView countdown = findViewById(R.id.track_time_countdown);
        long countMillis = calendar.getTime().getTime()-System.currentTimeMillis();
        Log.i("TIM", String.valueOf(countMillis));
        new CountDownTimer(countMillis,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                countdown.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                countdown.setText("---");
            }

        }.start();
    }

    @Override
    public void onBackPressed() {
        //Do nothing
    }
}
