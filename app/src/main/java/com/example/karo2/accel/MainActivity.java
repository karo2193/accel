package com.example.karo2.accel;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    TextView textViewUp;
    TextView textViewDown;
    TextView textViewLeft;
    TextView textViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        textViewUp = (TextView) findViewById(R.id.textViewUp);
        textViewDown = (TextView) findViewById(R.id.textViewDown);
        textViewLeft = (TextView) findViewById(R.id.textViewLeft);
        textViewRight = (TextView) findViewById(R.id.textViewRight);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            /*if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                }

                last_x = x;
                last_y = y;
                last_z = z;
            }*/

            if(x<0 && Math.abs(x)>Math.abs(y)) {
                textViewRight.setText("Prawo");
                textViewLeft.setText("");
                textViewUp.setText("");
                textViewDown.setText("");
            }
            else if(x>0 && Math.abs(x)>Math.abs(y)) {
                textViewRight.setText("");
                textViewLeft.setText("Lewo");
                textViewUp.setText("");
                textViewDown.setText("");
            }

            if(y<0 && Math.abs(x)<Math.abs(y)) {
                textViewUp.setText("Góra");
                textViewDown.setText("");
                textViewRight.setText("");
                textViewLeft.setText("");
            }
            else if(y>0 && Math.abs(x)<Math.abs(y)) {
                textViewUp.setText("");
                textViewDown.setText("Dół");
                textViewRight.setText("");
                textViewLeft.setText("");
            }
        }
    }

    // pauza i wznowienie w przypadku zawieszenia aplikacji
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
