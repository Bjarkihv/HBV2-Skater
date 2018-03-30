package isbhv2.hi.notandi.skater;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.IDNA;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import isbhv2.hi.notandi.skater.controller.FindSpotActivity;
import isbhv2.hi.notandi.skater.controller.LoginActivity;
import isbhv2.hi.notandi.skater.controller.NewReviewActivity;
import isbhv2.hi.notandi.skater.controller.ResultsActivity;
import isbhv2.hi.notandi.skater.controller.ReviewsActivity;
import isbhv2.hi.notandi.skater.controller.UserAreaActivity;
import isbhv2.hi.notandi.skater.service.CheckInOutRequest;
import isbhv2.hi.notandi.skater.service.GetReviewsRequest;
import isbhv2.hi.notandi.skater.service.LoginRequest;
import isbhv2.hi.notandi.skater.service.SearchRequest;

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
        final Button writeRev = (Button) findViewById(R.id.bWriteRev);
        final Button getRevs = (Button) findViewById(R.id.bReviews);

        mMap = googleMap;

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
        else{
            mMap.setMyLocationEnabled(true);
        }

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
        LatLngBounds findSpot = new LatLngBounds( spot, spot);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(findSpot.getCenter(), 15));
        //mMap.moveCamera(CameraUpdateFactory.zoomBy(10));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(spot));

        checkInLabel.setText("");
        if(!currentUser.spot.equals("None")) {
            bCheckIn.setEnabled(false);
            checkInLabel.setText("Þú ert nú þegar skráð/ur inn á stað.");
        }



        writeRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewIntent = new Intent(InfoMapsActivity.this, NewReviewActivity.class);
                reviewIntent.putExtra("nafn", nafn);
                startActivity(reviewIntent);
            }
        });

        getRevs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(InfoMapsActivity.this, ReviewsActivity.class);
                                Log.d("jsonArray: ", jsonResponse.toString());
                                String rat;
                                String tit;
                                String rev;
                                String usr;


                                for(int i = 0; i < jsonResponse.length()-1; i++){
                                    JSONArray jsonArray = jsonResponse.getJSONArray(Integer.toString(i));
                                    Log.d("jsonArray: ", jsonArray.toString());

                                    rat = jsonArray.getString(0);
                                    tit = jsonArray.getString(1);
                                    rev = jsonArray.getString(2);
                                    usr = jsonArray.getString(3);

                                    Log.d("Allt:", rev + " " + tit + " " + rev + " " + usr );

                                    intent.putExtra("rating"+Integer.toString(i), rat);
                                    intent.putExtra("title"+Integer.toString(i), tit);
                                    intent.putExtra("review"+Integer.toString(i), rev);
                                    intent.putExtra("user"+Integer.toString(i), usr);
                                }
                                intent.putExtra("length", jsonResponse.length());


                                InfoMapsActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(InfoMapsActivity.this);
                                builder.setMessage("Engar niðurstöður fundust")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                GetReviewsRequest getReviewsRequest = new GetReviewsRequest(nafn, responseListener);
                RequestQueue queue = Volley.newRequestQueue(InfoMapsActivity.this);
                queue.add(getReviewsRequest);
            }
        });



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
