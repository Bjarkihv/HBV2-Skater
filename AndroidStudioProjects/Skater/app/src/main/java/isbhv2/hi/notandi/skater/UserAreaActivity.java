package isbhv2.hi.notandi.skater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView nsUsername = (TextView) findViewById(R.id.nsUserLabel);
        final TextView nsWelcome = (TextView) findViewById(R.id.nsLabel);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        nsUsername.setText(username);
    }
}
