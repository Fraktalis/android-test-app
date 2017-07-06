package fraktalis.androidtestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import fraktalis.androidtestapp.model.Counter;

public class CounterActivity extends AppCompatActivity {

    protected Counter counter;

    protected long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.counter = new Counter();
        this.startTime = System.currentTimeMillis();
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        TextView greetingsDisplay = (TextView) findViewById(R.id.greetings);

        TextView average = (TextView) findViewById(R.id.average_click);
        TextView total = (TextView) findViewById(R.id.total_click);
        Button clicker = (Button) findViewById(R.id.clicker);
        ProgressBar bar = (ProgressBar) findViewById(R.id.click_progress);
        bar.setMax(1000);
        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.addValue(1f);
                updateCounter(counter, average);
                updateTotal(counter, total);
            }
        });

        TextView dateDisplay = (TextView) findViewById(R.id.current_date);
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Date noteTS = Calendar.getInstance().getTime();
                                String time = DateFormat.format("hh:mm:ss", noteTS).toString();
                                String date = DateFormat.format("dd MMMMM yyyy", noteTS).toString();
                                dateDisplay.setText(getString(R.string.front_date_time, date, time));
                                double averageClick = updateCounter(counter, average);
                                updateTotal(counter, total);
                                updateProgressBar(averageClick, bar);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        if (intent != null) {
            String name = intent.getStringExtra(StarterActivity.EXTRA_LOGIN);
            String pass = intent.getStringExtra(StarterActivity.EXTRA_PASSWORD);
            greetingsDisplay.setText(getString(R.string.front_greetings, name, pass));
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void updateTotal(Counter counter, TextView total) {
        total.setText(getString(R.string.front_total_click, (int)counter.getValue()));
    }

    public double updateCounter(Counter counter, TextView counterView) {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        double averageClick = counter.getValue() / elapsed;
        counterView.setText(getString(R.string.front_average_count, averageClick, elapsed));

        return averageClick;
    }

    public void updateProgressBar(double averageClick, ProgressBar bar) {
        bar.setProgress((int)(100*averageClick));
    }
}
