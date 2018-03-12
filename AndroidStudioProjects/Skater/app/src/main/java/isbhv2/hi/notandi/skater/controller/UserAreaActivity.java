package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import isbhv2.hi.notandi.skater.InfoMapsActivity;
import isbhv2.hi.notandi.skater.MapsActivity;
import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.service.CheckInOutRequest;

import static isbhv2.hi.notandi.skater.controller.LoginActivity.currentUser;

public class UserAreaActivity extends AppCompatActivity {

    private Button findSpotLaunch;
    private Button addSpotLaunch;
    private Button checkOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        addSpotLaunch = (Button) findViewById(R.id.button);
        findSpotLaunch = (Button) findViewById(R.id.button2);
        checkOutButton = (Button) findViewById(R.id.button3);

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

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                currentUser.setSpot("None");

                                Intent intent = new Intent(UserAreaActivity.this, UserAreaActivity.class);
                                UserAreaActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                                builder.setMessage("Upp kom villa.")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                CheckInOutRequest checkInOutRequest = new CheckInOutRequest("None", currentUser.username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(checkInOutRequest);
            }
        });

        final TextView nsUsername = (TextView) findViewById(R.id.nsUserLabel);
        final TextView nsWelcome = (TextView) findViewById(R.id.nsLabel);
        final TextView checkInLabel = (TextView) findViewById(R.id.checkInLabel);

        if(currentUser.getSpot().equals("None")) {
            checkInLabel.setText("Þú ert ekki tékkaður/tékkuð inn á neinn stað");
            checkOutButton.setEnabled(false);
        }
        else {
            checkInLabel.setText("Þú ert að bretta á: " + currentUser.spot);
            checkOutButton.setEnabled(true);
        }

        String username = currentUser.username;

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
