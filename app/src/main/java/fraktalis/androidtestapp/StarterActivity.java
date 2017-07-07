package fraktalis.androidtestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fraktalis.androidtestapp.service.LoginHandler;
import fraktalis.androidtestapp.service.LoginHandlerInterface;

public class StarterActivity extends AppCompatActivity {

    static final String EXTRA_LOGIN = "user_login";
    static final String EXTRA_PASSWORD = "user_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSensor = new Intent(StarterActivity.this, SensorActivity.class);
                startActivity(intentSensor);
            }
        });

        final EditText login = (EditText) findViewById(R.id.name_text);
        final EditText pass = (EditText) findViewById(R.id.password_text);
        final Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StarterActivity.this, CounterActivity.class);
                String name = login.getText().toString();
                String password = pass.getText().toString();
                LoginHandlerInterface loginHandler = new LoginHandler();
                if (!loginHandler.log(name, password)) {
                    Toast.makeText(StarterActivity.this, R.string.action_login_error,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                intent.putExtra(EXTRA_LOGIN, name);
                intent.putExtra(EXTRA_PASSWORD, password);
                startActivity(intent);
            }
        });

        final Button gravityButton = (Button) findViewById(R.id.gravity);
        gravityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StarterActivity.this, GravityActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_starter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
