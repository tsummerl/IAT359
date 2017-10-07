package summerland.twilight.lab4sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    TextView sensorListText;
    Sensor accelerometer;

    EditText accelX;
    EditText accelY;
    EditText accelZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorListText = (TextView) findViewById(R.id.SensorListTextView);

        accelX = (EditText) findViewById(R.id.accelX);
        accelY = (EditText) findViewById(R.id.accelY);
        accelZ = (EditText) findViewById(R.id.accelZ);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String sensorInfo = "";
        for(Sensor sensor : sensorList)
        {
            sensorInfo = sensorInfo + sensor.getName() + "\n";
        }
        sensorListText.setText(sensorInfo);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(this);

        super.onPause();
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();

        if(type == Sensor.TYPE_ACCELEROMETER)
        {
            float [] aVals = sensorEvent.values;
            accelX.setText("AccelX: " + aVals[0]);
            accelY.setText("AccelY: " + aVals[1]);
            accelZ.setText("AccelZ: " + aVals[2]);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
