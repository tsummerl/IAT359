package com.example.jake.summerlandtwilight_a2;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.hardware.Sensor;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.jake.summerlandtwilight_a2.R.layout.sensor_layout;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.MyViewHolder> {

    public ArrayList<Sensor> list; // a list of the sensors

    public SensorAdapter(ArrayList<Sensor> list) {
        this.list = list;
    } //initialize the list

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public SensorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new MyViewHolder object from the sensor_layout view
        View v = LayoutInflater.from(parent.getContext()).inflate(sensor_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SensorAdapter.MyViewHolder holder, int position) {
        //give the MyViewHolder Object the sensor name in the text view and an
        // int value for it's sensorType
        holder.sensorNameEntry.setText(list.get(position).getName());
        holder.sensorType = list.get(position).getType();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView sensorNameEntry;
        public int sensorType;
        public LinearLayout myLayout;
        private final String SENSORTYPE = "SENSOR_TYPE";
        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            // set up member variables
            myLayout = (LinearLayout) itemView;
            sensorNameEntry = (TextView) itemView.findViewById(R.id.sensorNameEntry);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            // when we click the sensor create a new intent.
            // pass the sensor type so we can get the sensor information in the new activity
            Intent intent = new Intent(context, SensorActivity.class);
            intent.putExtra(SENSORTYPE, sensorType);
            context.startActivity(intent);
        }
    }
}