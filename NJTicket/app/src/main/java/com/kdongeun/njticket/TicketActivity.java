package com.kdongeun.njticket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.kdongeun.njticket.databinding.ActivityMainBinding;
import com.kdongeun.njticket.databinding.ActivityTicketBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TicketActivity extends AppCompatActivity {

    private static final String TAG = "TicketActivity";

    private ActivityTicketBinding binding;

    private int countDownMin;
    private int countDownSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ticket);

        Intent intent = getIntent();
        String strCount = intent.getExtras().getString(getString(R.string.PEOPLE_COUNT));
        if(Integer.parseInt(strCount) > 1 ){
            binding.tvPeopleCount.setText(strCount + " Adults");
        }else{
            binding.tvPeopleCount.setText(strCount + " Adult");
        }

        String strZone = intent.getExtras().getString(getString(R.string.ZONE));
        binding.tvZone.setText(strZone);

        String hexColor = intent.getExtras().getString(getString(R.string.HEX_COLOR));
        int ticketColor = intent.getExtras().getInt(getString(R.string.INT_TICKET_COLOR));

        int bar1Color = intent.getExtras().getInt(getString(R.string.INT_BAR1_COLOR));
        binding.tvBar1.setBackgroundColor(bar1Color);
        int bar2Color = intent.getExtras().getInt(getString(R.string.INT_BAR2_COLOR));
        binding.tvBar2.setBackgroundColor(bar2Color);
        int bar3Color = intent.getExtras().getInt(getString(R.string.INT_BAR3_COLOR));
        binding.tvBar3.setBackgroundColor(bar3Color);

        setTime();
        setDate();

        countDownMin = 59;
        countDownSec = 59;

        setCountDown();

        blink();

        Log.d(TAG,"onClick : count = " + strCount + ", hexColor = " + hexColor);

        int colors[] = { ticketColor , 0xFFFFFF , ticketColor };

        GradientDrawable bgDrawable = (GradientDrawable)binding.llBackGround.getBackground();

        bgDrawable.setColors(colors);

        binding.llBackGround.setBackground(bgDrawable);

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(binding.llBar.getVisibility() == View.VISIBLE){
                            binding.llBar.setVisibility(View.INVISIBLE);
                        }else{
                            binding.llBar.setVisibility(View.VISIBLE);
                            setTime2();
                            setCountDown();
                        }
                        blink();
                    }
                });
            }
        }).start();
    }

    private void setTime(){
        Date date = new Date();

        SimpleDateFormat timeSdf = new SimpleDateFormat(":mm:ss");
        SimpleDateFormat hourSdf = new SimpleDateFormat("H");

        String strCurrentHour = hourSdf.format(date);
        Log.d(TAG,strCurrentHour);
        String strCurrentTime = "";

        if(Integer.parseInt(strCurrentHour) == 0) {
            strCurrentTime = "12" + timeSdf.format(date) + " AM";
        }else if(Integer.parseInt(strCurrentHour) == 12) {
            strCurrentTime = strCurrentHour + timeSdf.format(date) + " PM";
        }else if(Integer.parseInt(strCurrentHour) < 12) {
            strCurrentTime = "0" + strCurrentHour + timeSdf.format(date) + " AM";
        }else{
            strCurrentTime = "0" + (Integer.parseInt(strCurrentHour) - 12) + timeSdf.format(date) + " PM";
        }

        binding.tvCurrentTime.setText(strCurrentTime);
    }

    private void setTime2(){
        Date date = new Date();

        SimpleDateFormat timeSdf = new SimpleDateFormat("hh:mm:ss aaa", new Locale("en", "US"));
        binding.tvCurrentTime.setText(timeSdf.format(date));
    }

    private void setDate(){
        Date date = new Date();
        SimpleDateFormat dateSdf = new SimpleDateFormat("EEEE, MMM d, yyyy", new Locale("en", "US"));
        String strDate = dateSdf.format(date);
        binding.tvToday.setText(strDate);
    }

    private void setCountDown(){
        countDownSec--;
        if(countDownSec < 0) {
            countDownSec = 59;
            countDownMin--;
        }
        String strMin = Integer.toString(countDownMin);
        String strSec = Integer.toString(countDownSec);

        if(countDownMin < 10)
            strMin = "0" + Integer.toString(countDownMin);

        if(countDownSec < 10)
            strSec = "0" + Integer.toString(countDownSec);

        binding.tvExpireCount.setText("00:00:"+strMin+":"+strSec);
    }
}
