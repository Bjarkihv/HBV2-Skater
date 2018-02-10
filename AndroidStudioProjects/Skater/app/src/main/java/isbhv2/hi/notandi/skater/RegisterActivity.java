package isbhv2.hi.notandi.skater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Hvaða XML skrá þetta activity notar

        final EditText regEmail = (EditText) findViewById(R.id.inputEmail);
        final EditText regUsername = (EditText) findViewById(R.id.inputName);
        final EditText regPassword = (EditText) findViewById(R.id.inputPassword);
        final Button bRegister = (Button) findViewById(R.id.registerButton);
    }
}
