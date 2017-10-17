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

        //get sensor information
        int sensorVal = getIntent().getIntExtra(SENSORTYPE, -1);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorVal == -1)
        {
            Toast.makeText(this, "Something went wrong with the sensor", Toast.LENGTH_LONG);
            finish();
            // this shouldn't happen...
        }
        curSensor = sensorManager.getDefaultSensor(sensorVal);
        //get textviews
        textViewSensorName = (TextView) findViewById(R.id.sensorNameEntry);
        textViewSensorValue = (TextView) findViewById(R.id.sensorValueEntry);
        textViewSensorRange = (TextView) findViewById(R.id.sensorRangeEntry);
        textViewSensorResolution = (TextView) findViewById(R.id.sensorResolutionEntry);
        textViewSensorType = (TextView) findViewById(R.id.sensorTypeEntry);

        // set the sensor information in the text views
        textViewSensorName.setText("Sensor Name: " + curSensor.getName());
        textViewSensorRange.setText("Sensor Max Range: " + Float.toString(curSensor.getMaximumRange()));
        textViewSensorResolution.setText("Sensor Resolution: " + curSensor.getResolution());
        textViewSensorType.setText("Sensor Type: " + curSensor.getType());

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //register the sensor
        sensorManager.registerListener(this, curSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        //unregister sensor
        sensorManager.unregisterListener(this);
        super.onPause();
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        try{
            //get values from the sensor
            float [] vals = sensorEvent.values;
            StringBuilder stb = new StringBuilder("Values: ");
            for(float f: vals) //go through the float array and append the information
            {
                stb.append(Float.toString(f) + " ");
            }
            //update the textview
            textViewSensorValue.setText(stb.toString());
        }
        catch (ClassCastException e)
        {
            ; /// don't know what to do we have an exception, values aren't floats
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
