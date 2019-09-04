package com.example.barometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;


public class Barometer implements SensorEventListener {

    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor sensor;

    public Barometer(Context context) {
        mContext = context;
        enableSensor();
    }

    public void enableSensor() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        if (mSensorManager == null) {
            Log.v("sensor..", "Sensors not supported");
        }

        mSensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void disableSensor() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
            mSensorManager = null;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null) {
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            EventBus.getDefault().post(new BarometerMessage(event.values[0]));
        }
    }


}
