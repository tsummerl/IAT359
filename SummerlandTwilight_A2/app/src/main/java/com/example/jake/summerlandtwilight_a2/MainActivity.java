package com.example.jake.summerlandtwilight_a2;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{
    private RecyclerView recyclerView;
    private SensorManager sensorManager;
    private ArrayList<Sensor> sensorList;

    private SensorAdapter sensorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = new ArrayList<>(sensorManager.getSensorList(Sensor.TYPE_ALL));

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        sensorAdapter = new SensorAdapter(sensorList);
        recyclerView.setAdapter(sensorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
