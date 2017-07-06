package fraktalis.androidtestapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private static final int DARK = 0;
    private static final int AVERAGE = 1;
    private static final int HIGH = 2;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Vibrator vibrator;
    private int lightState = AVERAGE;
    private static final String ACTIVITY_NAME = "SensorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        ArrayList<String> devicesSensorsNames = new ArrayList<>();
        for (Sensor s : deviceSensors) {
            devicesSensorsNames.add(s.getName());
        }
        String[] sensorsNamesArray = new String[devicesSensorsNames.size()];
        devicesSensorsNames.toArray(sensorsNamesArray);
        ListView sensorsListView = (ListView) findViewById(R.id.sensors_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SensorActivity.this,
                android.R.layout.simple_list_item_1, sensorsNamesArray);
        sensorsListView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sensor app", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        int newLightState;
        Log.v("SensorActivity", String.valueOf(lux) + "lux");
        if (lux < 5f) {
            newLightState = DARK;
        } else if (lux >= 5f && lux < 1000f) {
            newLightState = AVERAGE;
        } else {
            newLightState = HIGH;
        }

        handleLightSate(newLightState);
    }

    public void handleLightSate(int newLightState) {
        if (newLightState != lightState) {
        lightState = newLightState;
            switch (lightState) {
                case DARK:
                    Toast.makeText(SensorActivity.this, R.string.front_dark_light,
                            Toast.LENGTH_SHORT).show();
                    break;
                case AVERAGE: Toast.makeText(SensorActivity.this, R.string.front_average_light,
                        Toast.LENGTH_SHORT).show();
                    break;
                case HIGH:
                    Toast.makeText(SensorActivity.this, R.string.front_high_light,
                            Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(500);
                    break;
                default:
                    Log.v("test", "test");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
