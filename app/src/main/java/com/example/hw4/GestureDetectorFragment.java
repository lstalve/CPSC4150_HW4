package com.example.hw4;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

public class GestureDetectorFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private ImageView emoji;
    private long lastUpdate;
    ImageView listeningAnimIV;

    AnimationDrawable listeningAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_gesture_detector, container, false);


        // Attaching listeningAnimIV to listening_animation in fragment_get_gesture.xml
        listeningAnimIV = myView.findViewById(R.id.listen_anim);

        // Added on Fri, Nov 15
        // Setting the ImageView background to the listening_anim animation list defined in
        // listening_anim.xml
        listeningAnimIV.setBackgroundResource(R.drawable.listening_anim);

        // Added on Fri, Nov 15
        // Attaching listeningAnimation Animation Drawable to listeningAnimIV's background, which
        // is listening_anim animation list
        listeningAnimation = (AnimationDrawable) listeningAnimIV.getBackground();
        listeningAnimIV.setVisibility(View.VISIBLE);
        listeningAnimation.start();
        emoji.findViewById(R.id.emoji);
        //sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();




        return myView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            //Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
            //       .show();
            emoji.setImageDrawable(getResources().getDrawable(R.drawable.waving));
            emoji.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
