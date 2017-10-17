package com.example.jake.summerlandtwilight_a2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.util.Stack;

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
    int bufferSizeInBytes;

    private boolean alreadyVib;

    //variables for movement
    private float[] gravity;
    int previousInclanation;
    private float accel;
    private float accelCurrent = SensorManager.GRAVITY_EARTH;
    private float accelLast = SensorManager.GRAVITY_EARTH;

    private int hitCount = 0;
    private double hitSum = 0;
    private double hitResult = 0;
    private final int ACCELSAMPLE_SIZE = 10;
    private final double ACCELTHRESHOLD = 0.2;
    private LimitedSizeQueue<Float> itemQueue;
    private boolean moving = false;

    /* Activity View items */
    private RecyclerView recyclerView;
    private TextView outputText;
    private Button noiseButton;
    private Button movementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get sensors and add them to an array list
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = new ArrayList<>(sensorManager.getSensorList(Sensor.TYPE_ALL));

        // Find the reclylerView, create a new adapter and set the recycler view's adapter
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        sensorAdapter = new SensorAdapter(sensorList);
        recyclerView.setAdapter(sensorAdapter);

        // find sensors and other hardware
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        soundGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Find views that are needed
        outputText = (TextView) findViewById(R.id.textOutput);
        noiseButton = (Button) findViewById(R.id.buttonDetectNoise);
        movementButton = (Button) findViewById(R.id.buttonDetectMovement);

        // set onclick listeners
        movementButton.setOnClickListener(this);
        noiseButton.setOnClickListener(this);

        //request permissions for the android audio, doesn't seem to happen automatically through the manifest
        requestRecordAudioPermission();
        itemQueue = new LimitedSizeQueue<>(ACCELSAMPLE_SIZE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ; // do nothing
    }

    @Override
    protected void onResume()
    {
        super.onResume();
//        register sensors
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        alreadyVib = true;
        try{
            //make the buffer for the audio recording
            bufferSizeInBytes = AudioRecord.getMinBufferSize(SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes);
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) //attempt to record
            {
                audioRecord.startRecording();
            }
            else{ //can't record, disable button since we can't detect noise. Release the recording
                audioRecord.release();
                noiseButton.setEnabled(false);
            }
        }
        catch (Exception e)
        {
            Log.e("MICROPHONE", "Exception", e);
        }
    }

    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(this);
        if(audioRecord != null)
        {
            if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING)
            {
                audioRecord.stop(); //stop recording
            }
            //release the recording manager
            audioRecord.release();
        }
        super.onPause();
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] val = sensorEvent.values;
        int sensorType = sensorEvent.sensor.getType();
        switch (sensorType) //loop through the different sensors
        {
            case Sensor.TYPE_LIGHT:
                if(val[0] < 0.5)
                {
                    soundGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200); //no light
                }
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float x = val[0];
                float y = val[1];
                float z = val[2];
                //get movement information
                accelLast = accelCurrent;
                float norm_Of_g =(float) Math.sqrt(x * x + y * y + z * z);
                accelCurrent = norm_Of_g;
                float delta = accelCurrent - accelLast;
                //get accelerometer movement
                accel = accel * 0.9f + delta;
                itemQueue.add(accel);
                hitResult = 0;
                for(Float f: itemQueue)
                {
                    hitResult += f;
                }
                hitResult = hitResult/itemQueue.size();
                if (hitResult > ACCELTHRESHOLD) {
                    moving = true;
                } else {
                    moving = false;
                }
                // Normalize the accelerometer vector
                x = (x / norm_Of_g);
                y = (y / norm_Of_g);
                z = (z / norm_Of_g);
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(y)));
                Log.i("tag","incline is:"+inclination);

                if (inclination < 95 && inclination > 85 && previousInclanation < 95 && inclination > 85) //the incline should be 90, have a range due to sensor sensitivity
                {
                    if(!alreadyVib) // if we haven't already vibrated for this "table flatness"
                    {
                        alreadyVib = true; //now we have vibrated
                        if(vibrator.hasVibrator())
                        {
                            vibrator.vibrate(5000); //vibrate for 5 seconds
                            Toast.makeText(this,"device flat - beep!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //don't have a vibrator
                            Toast.makeText(this, "device flat - beep! No vibrator", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    alreadyVib = false; //the device isn't at 90, set boolean to true
                }
                previousInclanation = inclination;
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDetectNoise:
                short[] buffer = new short[bufferSizeInBytes];

                int bufferReadResult = 1;
                double lastLevel = 0.0;
                if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {

                    // read the audio recording
                    bufferReadResult = audioRecord.read(buffer, 0, bufferSizeInBytes);
                    double sumLevel = 0;
                    for (int i = 0; i < bufferReadResult; i++) {
                        sumLevel += Math.abs(buffer[i]);
                    }
                    lastLevel = Math.abs((sumLevel / bufferReadResult));
                }
                Log.i("NOISE", Double.toString(lastLevel));
                if (lastLevel > 100) { //this number was randomly choosen, When everything was really quiet I was getting numbers
                    // around the range 23-30
                    outputText.setText("Noise Detected");
                } else {
                    outputText.setText("Noise Not Detected");
                }
                break;
            case R.id.buttonDetectMovement:
                //movement button pressed. Get movement information based on the accel we calculate on
                if(moving)
                {
                    outputText.setText("Movement Detected");
                }
                else{
                    outputText.setText("Movement Not Detected");
                }


                break;
        }
    }
// this code was taken from :https://stackoverflow.com/questions/38033068/android-audiorecord-wont-initialize
    private void requestRecordAudioPermission() {
        //check API version, do nothing if API version < 23!
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("Activity", "Granted!");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("Activity", "Denied!");
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
