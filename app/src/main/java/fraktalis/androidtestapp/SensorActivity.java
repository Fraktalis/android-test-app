package fraktalis.androidtestapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private static final String ACTIVITY_NAME = "SensorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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

}
