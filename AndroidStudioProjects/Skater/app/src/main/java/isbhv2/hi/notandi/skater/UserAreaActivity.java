package isbhv2.hi.notandi.skater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText nsUsername = (EditText) findViewById(R.id.nsUsername);
        final TextView nsWelcome = (TextView) findViewById(R.id.nsLabel);
    }
}
