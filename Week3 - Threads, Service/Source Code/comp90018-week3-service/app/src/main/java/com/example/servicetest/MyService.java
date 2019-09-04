package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyService extends Service {

    public MyService() {
    }

    // Example for the Binder
    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {

        public void startDownload() {
            Log.d("MyService", "startDownload executed");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress executed");
            return 0;
        }

    }

    // Called when Activity perform bindService() method
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Example for Eventbus
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new MessageEvent(MessageEvent.SERVICE, "Hello from Service!"));

        // Example for implement foreground service
//        Log.d("MyService", "onCreate executed");
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle("This is content title")
//                .setContentText("This is content text")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                .setContentIntent(pi)
//                .build();
//        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        //Unregister EventBus after stop service to avoid OOM (out-of-memory)
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }

    //called when startService() method is executed
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }

    //method to process when receiving MessageEvent
    @Subscribe
    public void onMessageEventService(MessageEvent event) {
        if (event.type == MessageEvent.ACTIVITY)
            Log.d("MyService", "Activity Message Content: " + event.message);
    }

}
