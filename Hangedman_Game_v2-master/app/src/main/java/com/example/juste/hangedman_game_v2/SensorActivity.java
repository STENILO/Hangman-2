package com.example.juste.hangedman_game_v2;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class SensorActivity extends Activity implements SensorEventListener {
    private static final float shake_gravity = 2.7F;
    private static final int shake_time = 500;
    private static final int shake_reset_time = 3000;

    private OnShakeListener shake_Listener;
    private long shakeTime;
    private int shakeCount;


    public SensorActivity() {

    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.shake_Listener = listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (shake_Listener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;


            float force = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);


            if (force > shake_gravity) {
                final long now = System.currentTimeMillis();

                if (shakeTime + shake_time > now) {
                    return;
                }


                if (shakeTime + shake_reset_time < now) {
                    shakeCount = 0;
                }

                shakeTime = now;
                shakeCount++;

                shake_Listener.onShake(shakeCount);
            }

        }}

        @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){

        }
    }


