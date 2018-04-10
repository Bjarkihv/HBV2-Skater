package isbhv2.hi.notandi.skater.controller;

/*
Activity sem sér um að birta notendasvæðið þar sem notandi
velur hvað hann vill gera í forritinu. Þetta activity birtir
líka upplýsingar um hvort notandi sér skráður inn á einhvern
stað.
 */

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import isbhv2.hi.notandi.skater.MapsActivity;
import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.service.CheckInOutRequest;

import static isbhv2.hi.notandi.skater.controller.LoginActivity.currentUser;

public class UserAreaActivity extends AppCompatActivity {

    private LinearLayout findSpotLaunch;
    private LinearLayout addSpotLaunch;
    private LinearLayout checkOutButton;
    private LinearLayout findPeopleLaunch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        addSpotLaunch = (LinearLayout) findViewById(R.id.button);
        findSpotLaunch = (LinearLayout) findViewById(R.id.button2);
        checkOutButton = (LinearLayout) findViewById(R.id.button3);
        findPeopleLaunch = (LinearLayout) findViewById(R.id.button4);

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

        findPeopleLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity3();
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.lastSpot = currentUser.spot;
                currentUser.setSpot("None");

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
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

                CheckInOutRequest checkInOutRequest = new CheckInOutRequest(currentUser.lastSpot, currentUser.username, "true", responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(checkInOutRequest);
            }
        });

        final TextView nsUsername = (TextView) findViewById(R.id.nsUserLabel);
        final TextView spotLabel = (TextView) findViewById(R.id.spotLabel);
        final TextView checkInLabel = (TextView) findViewById(R.id.checkInLabel);
        final ImageButton checkInImage = (ImageButton) findViewById(R.id.checkInImg);

        if(currentUser.getSpot().equals("None")) {
            spotLabel.setText("Þú ert ekki tékkaður/tékkuð inn á neinn stað");
            checkOutButton.setBackgroundColor(Color.GRAY);
            checkOutButton.setEnabled(false);
            checkInImage.setImageResource(R.drawable.ic_action_cross);
        }
        else {
            checkInLabel.setText("Þú ert að bretta á:" + currentUser.spot);
            spotLabel.setText("Smelltu hérna til að skrá þig út");
            checkOutButton.setBackgroundColor(Color.WHITE);
            checkOutButton.setEnabled(true);
            checkInImage.setImageResource(R.drawable.ic_action_check);
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

    private void launchActivity3() {

        Intent intent = new Intent(this, FindPeopleListActivity.class);
        startActivity(intent);
    }
}