package isbhv2.hi.notandi.skater;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import isbhv2.hi.notandi.skater.controller.LoginActivity;
import isbhv2.hi.notandi.skater.controller.UserAreaActivity;
import isbhv2.hi.notandi.skater.service.CheckInOutRequest;
import isbhv2.hi.notandi.skater.service.LoginRequest;

import static isbhv2.hi.notandi.skater.controller.LoginActivity.currentUser;

public class InfoMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        final TextView spotName = (TextView) findViewById(R.id.spotName);
        final TextView spotDesc = (TextView) findViewById(R.id.spotDesc);
        final TextView spotCats = (TextView) findViewById(R.id.spotCats);
        final TextView checkInLabel = (TextView) findViewById(R.id.checkInLabel);
        final Button bCheckIn = (Button) findViewById(R.id.bCheckIn);

        mMap = googleMap;

        Intent intent = getIntent();
        final String nafn = intent.getStringExtra("nafn");
        final String lysing = intent.getStringExtra("lysing");
        final String flokkar = intent.getStringExtra("flokkar");
        final String lat = intent.getStringExtra("lat");
        final String lng = intent.getStringExtra("lng");
        final Double dLat = Double.parseDouble(lat);
        final Double dLng = Double.parseDouble(lng);

        spotName.setText(nafn);
        spotDesc.setText(lysing);
        spotCats.setText("Til staðar: " + flokkar);


        LatLng spot = new LatLng(dLat, dLng);
        mMap.addMarker(new MarkerOptions().position(spot).title(nafn));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(spot));
        checkInLabel.setText("");
        if(!currentUser.spot.equals("None")) {
            bCheckIn.setEnabled(false);
            checkInLabel.setText("Þú ert nú þegar skráð/ur inn á stað.");
        }

        bCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String spot = jsonResponse.getString("spot");
                                currentUser.setSpot(spot);

                                Intent intent = new Intent(InfoMapsActivity.this, UserAreaActivity.class);
                                InfoMapsActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(InfoMapsActivity.this);
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

                CheckInOutRequest checkInOutRequest = new CheckInOutRequest(nafn, currentUser.username, "false", responseListener);
                RequestQueue queue = Volley.newRequestQueue(InfoMapsActivity.this);
                queue.add(checkInOutRequest);
            }
        });


    }
}
