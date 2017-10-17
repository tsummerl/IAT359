package com.example.jake.summerlandtwilight_a2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;

import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, SensorEventListener, View.OnClickListener{
    public static final int SAMPLERATE = 8000;
    private SensorManager sensorManager;
    private ArrayList<Sensor> sensorList;

    private SensorAdapter sensorAdapter;

    private Sensor lightSensor;
    private Sensor accelerometer;
    private ToneGenerator soundGenerator;
    private Vibrator vibrator;

    private AudioRecord audioRecord;

    private boolean alreadyVib;

    /* Activity View items */
    private RecyclerView recyclerView;
    private TextView outputText;
    private Button noiseButton;
    private Button movementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = new ArrayList<>(sensorManager.getSensorList(Sensor.TYPE_ALL));

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        sensorAdapter = new SensorAdapter(sensorList);
        recyclerView.setAdapter(sensorAdapter);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        soundGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        alreadyVib = true;

        try{
            int bufferSizeInBytes = AudioRecord.getMinBufferSize(SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes);
        }
        catch (Exception e)
        {
            Log.e("MICROPHONE", "Exception", e);
        }
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
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        alreadyVib = true;
        //audioRecord.startRecording();
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
            case Sensor.TYPE_ACCELEROMETER:
                float x = val[0];
                float y = val[1];
                float z = val[2];
                float norm_Of_g =(float) Math.sqrt(x * x + y * y + z * z);

                // Normalize the accelerometer vector
                x = (x / norm_Of_g);
                y = (y / norm_Of_g);
                z = (z / norm_Of_g);
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(y)));
                Log.i("tag","incline is:"+inclination);

                if (inclination < 95 && inclination > 85)
                {
                    if(!alreadyVib)
                    {
                        alreadyVib = true;
                        if(vibrator.hasVibrator())
                        {
                            vibrator.vibrate(5000);
                            Toast.makeText(this,"device flat - beep!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(this, "device flat - beep! No vibrator", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    alreadyVib = false;
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onClick(View view) {

    }
}
