package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import isbhv2.hi.notandi.skater.R;

public class ResultsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        final TextView result = (TextView) findViewById(R.id.result);

        Intent intent = getIntent();
        String res = intent.getStringExtra("description");
        result.setText(res);
    }
    private void launchActivity() {

        Intent intent = new Intent(this, FindSpotActivity.class);
        startActivity(intent);
    }
}
