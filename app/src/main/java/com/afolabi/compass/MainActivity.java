package com.afolabi.compass;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager compassSensorManager;//declare object that is used to access sensor
    Sensor accelerometer;//object for reading output
    Sensor magnetometer;//object for reading output
    float azimuthAngle;
    TextView tvDegrees;
    ImageView ivCompass;
    private float currentDegree=0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compassSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);//
        accelerometer=compassSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer=compassSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    protected void onResume(){
        super.onResume();
        compassSensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_UI);
        compassSensorManager.registerListener(this,magnetometer,SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause(){
        super.onPause();
        compassSensorManager.unregisterListener(this);
    }

    float[] accelerationReadings;
    float[] magneticReadings;

    @Override
    public void onSensorChanged(SensorEvent event) {
        tvDegrees= findViewById(R.id.tvDegrees);
        ivCompass= findViewById(R.id.ivCompass);
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            accelerationReadings=event.values;
        if (event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
            magneticReadings=event.values;
        if (accelerationReadings !=null && magneticReadings !=null){
            float R[]=new float[9];
            float I[]=new float[9];
            boolean successfulReading =SensorManager.getRotationMatrix(R,I,accelerationReadings,magneticReadings);
            if (successfulReading){
                float orientation[]=new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuthAngle=orientation[0];
                float degrees=((azimuthAngle*180f)/3.14f);
                int degreeInt =Math.round(degrees);
                tvDegrees.setText(Integer.toString(degreeInt)+(char) 0x00B0+"to absolute north");
                RotateAnimation rotate= new RotateAnimation(currentDegree, -degreeInt, Animation.RELATIVE_TO_SELF, 0.5f
                , Animation.RELATIVE_TO_SELF, 0.5f);
                ivCompass.startAnimation(rotate);
                currentDegree=-degreeInt;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
