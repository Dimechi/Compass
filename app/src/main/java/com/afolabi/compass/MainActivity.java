package com.afolabi.compass;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager compassSensorManager;//declare object that is used to access sensor
    Sensor accelerometer;//object for reading output
    Sensor magnetometer;//object for reading output
    int Azimuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compassSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);//
        accelerometer=compassSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer=compassSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
