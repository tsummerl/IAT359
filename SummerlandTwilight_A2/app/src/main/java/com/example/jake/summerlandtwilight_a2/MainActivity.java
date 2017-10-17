package com.example.jake.summerlandtwilight_a2;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.hardware.SensorEventListener;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, SensorEventListener{
    private RecyclerView recyclerView;
    private SensorManager sensorManager;
    private ArrayList<Sensor> sensorList;

    private SensorAdapter sensorAdapter;

    private Sensor lightSensor;
    private ToneGenerator soundGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = new ArrayList<>(sensorManager.getSensorList(Sensor.TYPE_ALL));

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        sensorAdapter = new SensorAdapter(sensorList);
        recyclerView.setAdapter(sensorAdapter);

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        soundGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        LinearLayout clickedRow = (LinearLayout) view;

        Toast.makeText(this, "row ", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(this);

        super.onPause();
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] val = sensorEvent.values;
        int sensorType = sensorEvent.sensor.getType();
        switch (sensorType)
        {
            case Sensor.TYPE_LIGHT:
                if(val[0] < 0.5)
                {
                    soundGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200);
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
