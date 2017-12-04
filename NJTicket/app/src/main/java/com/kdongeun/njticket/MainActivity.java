package com.kdongeun.njticket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.kdongeun.njticket.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private int ticketColor;
    private int bar1Color;
    private int bar2Color;
    private int bar3Color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btTicketColor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int color = binding.picker.getColor();
                Log.d(TAG,"onClick : color = " + color);
                binding.tvTicketColor.setBackgroundColor(color);
                ticketColor = color;
            }
        });

        binding.btBar1Color.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int color = binding.picker.getColor();
                Log.d(TAG,"onClick : color = " + color);
                binding.tvBar1Color.setBackgroundColor(color);
                bar1Color = color;
            }
        });

        binding.btBar2Color.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int color = binding.picker.getColor();
                Log.d(TAG,"onClick : color = " + color);
                binding.tvBar2Color.setBackgroundColor(color);
                bar2Color = color;
            }
        });

        binding.btBar3Color.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int color = binding.picker.getColor();
                Log.d(TAG,"onClick : color = " + color);
                binding.tvBar3Color.setBackgroundColor(color);
                bar3Color = color;
            }
        });

        binding.btCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strCount = (String) binding.etPeople.getText().toString();
                String strZone = (String) binding.etZone.getText().toString();
                int color = binding.picker.getColor();
                String hexColor = Integer.toHexString(color).substring(2,8);
                Log.d(TAG,"onClick : count = " + strCount + ", color = " + color + ", hexColor = " + hexColor);

                Intent intent = new Intent(getApplicationContext(), TicketActivity.class);
                intent.putExtra(getString(R.string.PEOPLE_COUNT),strCount);
                intent.putExtra(getString(R.string.ZONE),strZone);
                intent.putExtra(getString(R.string.HEX_COLOR),hexColor);
                intent.putExtra(getString(R.string.INT_TICKET_COLOR),ticketColor);
                intent.putExtra(getString(R.string.INT_BAR1_COLOR),bar1Color);
                intent.putExtra(getString(R.string.INT_BAR2_COLOR),bar2Color);
                intent.putExtra(getString(R.string.INT_BAR3_COLOR),bar3Color);
                startActivity(intent);
            }
        });
    }
}
