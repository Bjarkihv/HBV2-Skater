package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import java.util.Random;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import isbhv2.hi.notandi.skater.InfoMapsActivity;
import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.service.SearchRequest;

public class FindSpotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_spot);

        final Button bSearch = (Button) findViewById(R.id.bLeita);

        final CheckBox checkTroppur = (CheckBox) findViewById(R.id.cbTroppurS);
        final CheckBox checkHandrid = (CheckBox) findViewById(R.id.cbHandridS);
        final CheckBox checkRampur = (CheckBox) findViewById(R.id.cbRampurS);
        final CheckBox checkVetur = (CheckBox) findViewById(R.id.cbVeturS);
        final CheckBox checkInnandyra = (CheckBox) findViewById(R.id.cbInnandyraS);
        final CheckBox checkDropp = (CheckBox) findViewById(R.id.cbDroppS);
        final CheckBox checkUpplyst = (CheckBox) findViewById(R.id.cbUpplystS);

        final Button randomPlace = (Button) findViewById(R.id.button6);





        randomPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String troppur = Boolean.toString(checkTroppur.isChecked());
                final String handrid = Boolean.toString(checkHandrid.isChecked());
                final String rampur = Boolean.toString(checkRampur.isChecked());
                final String vetur = Boolean.toString(checkVetur.isChecked());
                final String innandyra = Boolean.toString(checkInnandyra.isChecked());
                final String dropp = Boolean.toString(checkDropp.isChecked());
                final String upplyst = Boolean.toString(checkUpplyst.isChecked());


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            Intent intent = new Intent(FindSpotActivity.this, ResultsActivity.class);

                            if(success){
                                /*
                                Log.d("myTag", jsonResponse.toString());
                                Log.d("myTag", jsonResponse.getString("0"));
                                Log.d("myTag", jsonResponse.getJSONArray("0").toString());
                                JSONArray bla = jsonResponse.getJSONArray("0");
                                Log.d("myTag", bla.getString(1));
                                */

                                String nfn;
                                String lsng;
                                String lat;
                                String lng;
                                String tr;
                                String hr;
                                String rmp;
                                String vtr;
                                String indr;
                                String drp;
                                String upl;
                                String cin;

                                Random rnd = new Random();
                                int randTala = rnd.nextInt(jsonResponse.length());

                                int randEnd = jsonResponse.length()-1;
                                int randBeg = jsonResponse.length()-2;

                                for(int i = 0; i < jsonResponse.length()-randEnd; i++){
                                    JSONArray jsonArray = jsonResponse.getJSONArray(Integer.toString(randTala));
                                    Log.d("jsonArray: ", jsonArray.toString());

                                    nfn = jsonArray.getString(2);
                                    lsng = jsonArray.getString(3);
                                    lat = jsonArray.getString(0);
                                    lng = jsonArray.getString(1);
                                    tr = jsonArray.getString(4);
                                    hr = jsonArray.getString(5);
                                    rmp = jsonArray.getString(6);
                                    vtr = jsonArray.getString(7);
                                    indr = jsonArray.getString(8);
                                    drp = jsonArray.getString(9);
                                    upl = jsonArray.getString(10);
                                    cin = jsonArray.getString(11);

                                    Log.d("Allt:", nfn + " " + lsng + " " + lat + " " + lng + " " + tr + " " + hr
                                            + " " + hr + " " + rmp + " " + vtr + " " + indr + " " + drp + " " + upl + " " + cin);
                                    Log.d("Intentnafn, dæmi:", "nafn"+Integer.toString(2));

                                    intent.putExtra("nafn"+Integer.toString(i), nfn);
                                    intent.putExtra("lysing"+Integer.toString(i), lsng);
                                    intent.putExtra("lat"+Integer.toString(i), lat);
                                    intent.putExtra("lng"+Integer.toString(i), lng);
                                    intent.putExtra("troppur"+Integer.toString(i), tr);
                                    intent.putExtra("handrid"+Integer.toString(i), hr);
                                    intent.putExtra("rampur"+Integer.toString(i), rmp);
                                    intent.putExtra("vetur"+Integer.toString(i), vtr);
                                    intent.putExtra("innandyra"+Integer.toString(i), indr);
                                    intent.putExtra("dropp"+Integer.toString(i), drp);
                                    intent.putExtra("upplyst"+Integer.toString(i), upl);
                                    intent.putExtra("checkedIn"+Integer.toString(i), cin);
                                }
                                intent.putExtra("length", jsonResponse.length()-randBeg);
                                Log.d("jsonResponse stórt?   ", String.valueOf(jsonResponse.length()));



                                FindSpotActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindSpotActivity.this);
                                builder.setMessage("Ekkert fannst")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                SearchRequest searchRequest = new SearchRequest(troppur, handrid, rampur,
                        vetur, innandyra, dropp, upplyst, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindSpotActivity.this);
                queue.add(searchRequest);
            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String troppur = Boolean.toString(checkTroppur.isChecked());
                final String handrid = Boolean.toString(checkHandrid.isChecked());
                final String rampur = Boolean.toString(checkRampur.isChecked());
                final String vetur = Boolean.toString(checkVetur.isChecked());
                final String innandyra = Boolean.toString(checkInnandyra.isChecked());
                final String dropp = Boolean.toString(checkDropp.isChecked());
                final String upplyst = Boolean.toString(checkUpplyst.isChecked());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            Intent intent = new Intent(FindSpotActivity.this, ResultsActivity.class);

                            if(success){
<<<<<<< HEAD
                                Intent intent = new Intent(FindSpotActivity.this, ResultsActivity.class);
=======
                                /*
                                Log.d("myTag", jsonResponse.toString());
                                Log.d("myTag", jsonResponse.getString("0"));
                                Log.d("myTag", jsonResponse.getJSONArray("0").toString());
                                JSONArray bla = jsonResponse.getJSONArray("0");
                                Log.d("myTag", bla.getString(1));
                                */
>>>>>>> 3d854893177656bbbdfc4c4900864045027da32b

                                String nfn;
                                String lsng;
                                String lat;
                                String lng;
                                String tr;
                                String hr;
                                String rmp;
                                String vtr;
                                String indr;
                                String drp;
                                String upl;
                                String cin;

                                for(int i = 0; i < jsonResponse.length()-1; i++){
                                    JSONArray jsonArray = jsonResponse.getJSONArray(Integer.toString(i));
                                    Log.d("jsonArray: ", jsonArray.toString());

                                    nfn = jsonArray.getString(2);
                                    lsng = jsonArray.getString(3);
                                    lat = jsonArray.getString(0);
                                    lng = jsonArray.getString(1);
                                    tr = jsonArray.getString(4);
                                    hr = jsonArray.getString(5);
                                    rmp = jsonArray.getString(6);
                                    vtr = jsonArray.getString(7);
                                    indr = jsonArray.getString(8);
                                    drp = jsonArray.getString(9);
                                    upl = jsonArray.getString(10);
                                    cin = jsonArray.getString(11);

                                    Log.d("Allt:", nfn + " " + lsng + " " + lat + " " + lng + " " + tr + " " + hr
                                    + " " + hr + " " + rmp + " " + vtr + " " + indr + " " + drp + " " + upl + " " + cin);
                                    Log.d("Intentnafn, dæmi:", "nafn"+Integer.toString(i));

                                    intent.putExtra("nafn"+Integer.toString(i), nfn);
                                    intent.putExtra("lysing"+Integer.toString(i), lsng);
                                    intent.putExtra("lat"+Integer.toString(i), lat);
                                    intent.putExtra("lng"+Integer.toString(i), lng);
                                    intent.putExtra("troppur"+Integer.toString(i), tr);
                                    intent.putExtra("handrid"+Integer.toString(i), hr);
                                    intent.putExtra("rampur"+Integer.toString(i), rmp);
                                    intent.putExtra("vetur"+Integer.toString(i), vtr);
                                    intent.putExtra("innandyra"+Integer.toString(i), indr);
                                    intent.putExtra("dropp"+Integer.toString(i), drp);
                                    intent.putExtra("upplyst"+Integer.toString(i), upl);
                                    intent.putExtra("checkedIn"+Integer.toString(i), cin);
                                }
                                intent.putExtra("length", jsonResponse.length());




                            FindSpotActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindSpotActivity.this);
                                builder.setMessage("Ekkert fannst")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                SearchRequest searchRequest = new SearchRequest(troppur, handrid, rampur,
                        vetur, innandyra, dropp, upplyst, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindSpotActivity.this);
                queue.add(searchRequest);
            }
        });


    }

}
