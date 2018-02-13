package isbhv2.hi.notandi.skater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class UserAreaActivity extends AppCompatActivity {

    private Button findSpotLaunch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        findSpotLaunch = (Button) findViewById(R.id.button2);

        findSpotLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });

        final EditText nsUsername = (EditText) findViewById(R.id.nsUsername);
        final TextView nsWelcome = (TextView) findViewById(R.id.nsLabel);
    }
    private void launchActivity() {

        Intent intent = new Intent(this, FindSpotActivity.class);
        startActivity(intent);
    }
}
