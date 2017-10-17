package com.example.jake.summerlandtwilight_a2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class SensorActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    Sensor curSensor;

    TextView textViewSensorName;
    TextView textViewSensorValue;
    TextView textViewSensorRange;
    TextView textViewSensorResolution;
    TextView textViewSensorType;
    private final String SENSORTYPE = "SENSOR_TYPE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        int sensorVal = getIntent().getIntExtra(SENSORTYPE, -1);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        curSensor = sensorManager.getDefaultSensor(sensorVal);

        textViewSensorName = (TextView) findViewById(R.id.sensorNameEntry);
        textViewSensorValue = (TextView) findViewById(R.id.sensorValueEntry);
        textViewSensorRange = (TextView) findViewById(R.id.sensorRangeEntry);
        textViewSensorResolution = (TextView) findViewById(R.id.sensorResolutionEntry);
        textViewSensorType = (TextView) findViewById(R.id.sensorTypeEntry);

        textViewSensorName.setText("Sensor Name: " + curSensor.getName());
        textViewSensorRange.setText("Sensor Max Range: " + Float.toString(curSensor.getMaximumRange()));
        textViewSensorResolution.setText("Sensor Resolution: " + curSensor.getResolution());
        textViewSensorType.setText("Sensor Type: " + curSensor.getType());

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, curSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(this);

        super.onPause();
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        try{
            float [] vals = sensorEvent.values;
            StringBuilder stb = new StringBuilder("Values: ");
            for(float f: vals)
            {
                stb.append(Float.toString(f) + " ");
            }
            textViewSensorValue.setText(stb.toString());
        }
        catch (ClassCastException e)
        {
            ; /// don't know what to do
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
