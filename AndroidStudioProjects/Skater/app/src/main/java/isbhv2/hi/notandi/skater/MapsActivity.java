package isbhv2.hi.notandi.skater;

/*
Activity sem sér um að senda upplýsingar frá notanda
í newSpotRequest, sem setur svo nýjan spot í gagnagrunninn.
Hér er  líka GoogleMaps virkni, þar sem notandi notar GM til
að merka staðinn inn á kort.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import isbhv2.hi.notandi.skater.controller.CameraActivity;
import isbhv2.hi.notandi.skater.controller.UserAreaActivity;
import isbhv2.hi.notandi.skater.service.newSpotRequest;

import static java.util.Objects.isNull;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private LatLng spot = new LatLng(0.0, 0.0);
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    final int CAMERA_REQUEST = 1888;
    ImageView mImageView;
    File photoFile = new File("");

    /* TODO
    * Gera verification snyrtilegra og
    * gera svo error messages skýrari.
    */
    public boolean verifySpotName(String s){
        if(s.length() < 20)
            return true;
        else
            return false;
    }

    public boolean verifyDescription(String s){
        if(s.length() < 200)
            return true;
        else
            return false;
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            mImageView.setImageBitmap(bitmap);
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(bitmap);

        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);

        Map config = new HashMap();
        config.put("cloud_name", "hbv2skater");
        config.put("api_key", "459114518896268");
        config.put("api_secret", "caVLsNO_5Amx89RBTroN2MUZP-w");
        //MediaManager.init(this, config);

        Cloudinary cloudinary = new Cloudinary(config);
        try {
            cloudinary.uploader().upload(f, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                "example",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final EditText spotNafn = (EditText) findViewById(R.id.nafnText);
        final EditText spotLysing = (EditText) findViewById(R.id.lysingText);
        final CheckBox checkTroppur = (CheckBox) findViewById(R.id.cbTroppur);
        final CheckBox checkHandrid = (CheckBox) findViewById(R.id.cbHandrid);
        final CheckBox checkRampur = (CheckBox) findViewById(R.id.cbRampur);
        final CheckBox checkVetur = (CheckBox) findViewById(R.id.cbVetur);
        final CheckBox checkInnandyra = (CheckBox) findViewById(R.id.cbInnandyra);
        final CheckBox checkDropp = (CheckBox) findViewById(R.id.cbDropp);
        final CheckBox checkUpplyst = (CheckBox) findViewById(R.id.cbUpplyst);
        final Button bSenda = (Button) findViewById(R.id.bSenda);
        final Button bMynd = (Button) findViewById(R.id.bMynd);
        final int CAMERA_REQUEST = 1888;
        mImageView = (ImageView) findViewById(R.id.mImageView);

        bMynd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });



        bSenda.setOnClickListener(new View.OnClickListener() {
            final String nafn = spotNafn.getText().toString();
            final String lysing = spotLysing.getText().toString();

            @Override
            public void onClick(View v) {
                if (spot.longitude == 0.0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setMessage("Vinsamlegast merktu staðinn inn á kortinu")
                            .setNegativeButton("Reyna aftur", null)
                            .create()
                            .show();
                }
                if (mCurrentPhotoPath == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setMessage("Vinsamlegast taktu mynd af staðnum")
                            .setNegativeButton("Reyna aftur", null)
                            .create()
                            .show();
                }
                if (nafn == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setMessage("Vinsamlegast gefðu staðnum nafn")
                            .setNegativeButton("Reyna aftur", null)
                            .create()
                            .show();
                }
                if (lysing == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setMessage("Vinsamlegast skrifaðu lýsingu um staðinn")
                            .setNegativeButton("Reyna aftur", null)
                            .create()
                            .show();
                }else {

                    final String troppur = Boolean.toString(checkTroppur.isChecked());
                    final String handrid = Boolean.toString(checkHandrid.isChecked());
                    final String rampur = Boolean.toString(checkRampur.isChecked());
                    final String vetur = Boolean.toString(checkVetur.isChecked());
                    final String innandyra = Boolean.toString(checkInnandyra.isChecked());
                    final String dropp = Boolean.toString(checkDropp.isChecked());
                    final String upplyst = Boolean.toString(checkUpplyst.isChecked());
                    galleryAddPic();
                    Double dLat = spot.latitude;
                    Double dLng = spot.longitude;
                    final String lat = dLat.toString();
                    final String lng = dLng.toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("myTag", "Response móttekið");

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                // Ef þetta tekst þá sendum við notanda aftur á login síðuna.
                                if (success) {
                                    Log.d("myTag", "Intent keyrt");
                                    Intent intent = new Intent(MapsActivity.this, UserAreaActivity.class);
                                    MapsActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                                    builder.setMessage("Skráning tókst ekki")
                                            .setNegativeButton("Reyna aftur", null)
                                            .create()
                                            .show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    newSpotRequest spotRequest = new newSpotRequest(nafn, lysing, troppur, handrid, rampur,
                            vetur, innandyra, dropp, upplyst, lat, lng, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
                    queue.add(spotRequest);
                }
            }

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Leyfi veitt
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else // Leyfi ekki veitt
                {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Þegar kortið er tilbúið til notkunar
        mMap = googleMap;

        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else{
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                spot = latLng;

                Double l1=latLng.latitude;
                Double l2=latLng.longitude;
                String coordl1 = l1.toString();
                String coordl2 = l2.toString();
                l1 = Double.parseDouble(coordl1);
                l2 = Double.parseDouble(coordl2);

                final TextView hnit = (TextView) findViewById(R.id.hnitText);
                hnit.setText(coordl1 + ", " + coordl2);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });

        LatLng goTo1 = new LatLng(64.137, -21.9179);
        LatLng goTo2 = new LatLng(64.146, -21.9248);
        LatLngBounds findSpot = new LatLngBounds( goTo1, goTo2 );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goTo1, 12));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(goTo1));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
    }


    protected synchronized void buildGoogleApiClient(){
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Þegar notandi færir sig um set

        lastLocation = location;

        if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Breytum markerinum eins og við viljum
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        if(client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Frá google.api.client.connectioncallbacks
        // Kallað á þetta þegar tækið tengist APIinu

        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // Fáum leyfi frá notanda til að sjá staðsetningu
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Notum FLAPI til að fá núverandi staðsetningu notanda
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }

    }

    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Frá google.api.client.onconnectionfailedlistener
        // Kallað á þetta þegar tækið tengist APIinu

    }
}
