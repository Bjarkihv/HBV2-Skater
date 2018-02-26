package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import isbhv2.hi.notandi.skater.MapsActivity;
import isbhv2.hi.notandi.skater.R;

public class UserAreaActivity extends AppCompatActivity {

    private Button findSpotLaunch;
    private Button addSpotLaunch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        findSpotLaunch = (Button) findViewById(R.id.button2);
        addSpotLaunch = (Button) findViewById(R.id.button);

        findSpotLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });

        addSpotLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity2();
            }
        });

        final TextView nsUsername = (TextView) findViewById(R.id.nsUserLabel);
        final TextView nsWelcome = (TextView) findViewById(R.id.nsLabel);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        nsUsername.setText(username);
    }
    private void launchActivity() {

        Intent intent = new Intent(this, FindSpotActivity.class);
        startActivity(intent);
    }

    private void launchActivity2() {

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
