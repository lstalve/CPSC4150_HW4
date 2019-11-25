package com.example.hw4;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GestureDetectorFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private ImageView emoji;
    private long lastUpdate;
    private ImageView listeningAnimIV;
    private AnimationDrawable listeningAnimation;
    private TextView emojiText;
    private Button sendButton;
    private String contact;
    private String emoji_string;

    public static GestureDetectorFragment newInstance(String inContact) {
        GestureDetectorFragment fragment = new GestureDetectorFragment();
        Bundle args = new Bundle();
        args.putString("contact", inContact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact = "";
        if(getArguments() != null) {
            contact = getArguments().getString("contact");
        }
    }

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
        emoji = myView.findViewById(R.id.emoji);
        emojiText = myView.findViewById(R.id.emoji_text);
        sendButton = myView.findViewById(R.id.send_button);

        // Added on Fri, Nov 15
        // Attaching listeningAnimation Animation Drawable to listeningAnimIV's background, which
        // is listening_anim animation list
        listeningAnimation = (AnimationDrawable) listeningAnimIV.getBackground();
        listeningAnimIV.setVisibility(View.VISIBLE);
        listeningAnimation.start();
        //emoji.findViewById(R.id.emoji);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //emoji_string = "Wasshup";
                sendSMS();
            }
        });


        return myView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            getGyroscope(event);
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getGyroscope(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.AXIS_Z * SensorManager.AXIS_Z);
        long actualTime = event.timestamp;
        if (z >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            int unicode = 0x1F60A;
            emoji_string = getEmojiByUnicode(unicode);

            lastUpdate = actualTime;
            //Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
            //       .show();
            //emoji.setImageDrawable(getResources().getDrawable(R.drawable.waving));
            //emoji.setVisibility(View.VISIBLE);

            emojiText.setText(emoji_string);
            emojiText.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.VISIBLE);
            listeningAnimIV.setVisibility(View.INVISIBLE);
            listeningAnimation.stop();
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
            int unicode = 0x1F60A;
            emoji_string = getEmojiByUnicode(unicode);

            lastUpdate = actualTime;
            //Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
            //       .show();
            //emoji.setImageDrawable(getResources().getDrawable(R.drawable.waving));
            //emoji.setVisibility(View.VISIBLE);

            emojiText.setText(emoji_string);
            emojiText.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.VISIBLE);
            listeningAnimIV.setVisibility(View.INVISIBLE);
            listeningAnimation.stop();
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

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    protected void sendSMS() {
        Uri uri = Uri.parse("smsto: " + contact);
        Intent intent = new Intent (Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", emoji_string);
        startActivity(intent);
    }
}