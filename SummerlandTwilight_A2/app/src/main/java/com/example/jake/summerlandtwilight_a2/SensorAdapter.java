package com.example.jake.summerlandtwilight_a2;

import android.content.Context;
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

    public ArrayList<Sensor> list;
    Context context;

    public SensorAdapter(ArrayList<Sensor> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public SensorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(sensor_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SensorAdapter.MyViewHolder holder, int position) {
        holder.sensorNameEntry.setText(list.get(position).getName());
        holder.sensorType = list.get(position).getType();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView sensorNameEntry;
        public int sensorType;
        public LinearLayout myLayout;

        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;
            sensorNameEntry = (TextView) itemView.findViewById(R.id.sensorNameEntry);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, String.valueOf(sensorType), Toast.LENGTH_LONG).show();
        }
    }
}