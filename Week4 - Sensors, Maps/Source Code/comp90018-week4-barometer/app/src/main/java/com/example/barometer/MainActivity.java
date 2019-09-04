package com.example.barometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    TextView pressure;
    Barometer barometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pressure = findViewById(R.id.barometer_value);

        barometer = new Barometer(this);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        barometer.disableSensor();
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BarometerMessage event) {
        pressure.setText(String.valueOf(event.getPressure()));

    }

}
