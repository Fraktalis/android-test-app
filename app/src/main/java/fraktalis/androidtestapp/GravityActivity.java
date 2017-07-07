package fraktalis.androidtestapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import java.util.Vector;

public class GravityActivity extends AppCompatActivity implements SensorEventListener {

    private long startRecord;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView acceleration;
    private TextView speed;
    private TextView position;
    private float[] gravityVector = {0f,0f,0f};
    private float[] accelerationVector = {0f,0f,0f};
    private float[] speedVector = {0f,0f,0f};
    private float[] positionVector = {0f,0f,0f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        acceleration = (TextView) findViewById(R.id.front_acceleration);
        speed = (TextView) findViewById(R.id.front_speed);
        position = (TextView) findViewById(R.id.front_position);
        startRecord = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        // unregister the sensor (désenregistrer le capteur)
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        /* Ce qu'en dit Google&#160;dans le cas de l'accéléromètre :
         * «&#160; Ce n'est pas nécessaire d'avoir les évènements des capteurs à un rythme trop rapide.
         * En utilisant un rythme moins rapide (SENSOR_DELAY_UI), nous obtenons un filtre
         * automatique de bas-niveau qui "extrait" la gravité  de l'accélération.
         * Un autre bénéfice étant que l'on utilise moins d'énergie et de CPU.&#160;»
         */
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_starter, menu);
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float alpha;
        long newRecordTime = System.currentTimeMillis();
        float elapsed = (newRecordTime - startRecord) / 1000f;
        alpha = 1f/(1f+(elapsed));

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float oldX = accelerationVector[0];
            float oldY = accelerationVector[1];
            float oldZ = accelerationVector[2];



            gravityVector[0] = alpha * gravityVector[0] + (1 - alpha) * event.values[0];
            gravityVector[1] = alpha * gravityVector[1] + (1 - alpha) * event.values[1];
            gravityVector[2] = alpha * gravityVector[2] + (1 - alpha) * event.values[2];
            accelerationVector[0] = event.values[0] - gravityVector[0];
            accelerationVector[1] = event.values[1] - gravityVector[1];
            accelerationVector[2] = event.values[2] - gravityVector[2];
            speedVector[0] = (oldX - accelerationVector[0]) * elapsed - speedVector[0];
            speedVector[1] = (oldY - accelerationVector[1]) * elapsed - speedVector[1];
            speedVector[2] = (oldZ - accelerationVector[2]) * elapsed - speedVector[2];
            positionVector[0] = (oldX - accelerationVector[0]) * elapsed - speedVector[0];
            speedVector[1] = (oldY - accelerationVector[1]) * elapsed - speedVector[1];
            speedVector[2] = (oldZ - accelerationVector[2]) * elapsed - speedVector[2];
            acceleration.setText(getString(R.string.front_acceleration, accelerationVector[0], accelerationVector[1], accelerationVector[2]));
            speed.setText(getString(R.string.front_speed, speedVector[0], speedVector[1], speedVector[2]));
            Log.v("GravityActivity", "Time elapsed : " + String.valueOf(elapsed) + "seconds");
            startRecord = System.currentTimeMillis();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
