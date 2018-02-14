package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.model.User;
import isbhv2.hi.notandi.skater.service.LoginRequest;

public class LoginActivity extends AppCompatActivity {
    public User currentUser = new User("Guest", "bla@bla.com", 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText logUsername = (EditText) findViewById(R.id.loginUsername);
        final EditText logPassword = (EditText) findViewById(R.id.loginPassword);
        final Button bLogin = (Button) findViewById(R.id.loginButton);
        final TextView registerLink = (TextView) findViewById(R.id.registerLink);

        // Ættum kannski að gera fragment klasa fyrir þetta
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("myTag", "This is my message");
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = logUsername.getText().toString();
                final String password = logPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String username = jsonResponse.getString("username");
                                currentUser.setUsername(username);
                                // prófa location fídusinn seinna þegar hann er kominn inn
                                //currentUser.setLocation(currentLocation);

                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("username", username);

                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Rangt notendanafn eða lykilorð")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
