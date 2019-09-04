package com.example.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService.DownloadBinder downloadBinder;

    // To ServiceConnection for monitoring the change of communication between Service and Activity
    private ServiceConnection connection = new ServiceConnection() {

        // called when disconnected to Service
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        // called when connecting to Service
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        Button bindService = (Button) findViewById(R.id.bind_service);
        Button unbindService = (Button) findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);

        // Register for EventBus Library
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        // Unregister to avoid Android OOM (out-of-memory)
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent); // Start Service
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent); // Stop Service
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE); // Bind Service
                break;
            case R.id.unbind_service:
                unbindService(connection); // Unbind Service
                break;
            default:
                break;
        }
    }

//  Method to process when receiving MessageEvent
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventActivity(MessageEvent event) {
        if (event.type == MessageEvent.SERVICE) {
            Log.d("MyService", "Service Message Content: " + event.message);
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTIVITY, "Hello from Activity!"));
        }
    }

}
