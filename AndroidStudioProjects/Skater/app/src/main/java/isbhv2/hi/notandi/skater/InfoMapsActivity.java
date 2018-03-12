package isbhv2.hi.notandi.skater;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        final TextView spotName = (TextView) findViewById(R.id.spotName);
        final TextView spotDesc = (TextView) findViewById(R.id.spotDesc);
        final TextView spotCats = (TextView) findViewById(R.id.spotCats);

        mMap = googleMap;

        Intent intent = getIntent();
        String nafn = intent.getStringExtra("nafn");
        String lysing = intent.getStringExtra("lysing");
        String flokkar = intent.getStringExtra("flokkar");
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");
        Double dLat = Double.parseDouble(lat);
        Double dLng = Double.parseDouble(lng);
        Log.d("myTag", lat + "    " + lng);

        spotName.setText(nafn);
        spotDesc.setText(lysing);
        spotCats.setText("Til staðar: " + flokkar);


        LatLng spot = new LatLng(dLat, dLng);
        mMap.addMarker(new MarkerOptions().position(spot).title(nafn));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(spot));
    }
}
