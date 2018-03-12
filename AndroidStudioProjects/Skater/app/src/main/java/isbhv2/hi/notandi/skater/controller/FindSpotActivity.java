package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

                            if(success){
                                Intent intent = new Intent(FindSpotActivity.this, ResultsActivity.class);
                                Log.d("myTag", jsonResponse.toString());

                                Log.d("myTag", "Success");
                                String nfn = jsonResponse.getString("nafn");
                                String lsng = jsonResponse.getString("lysing");
                                String lat = jsonResponse.getString("lat");
                                String lng = jsonResponse.getString("lng");
                                String tr = jsonResponse.getString("troppur");
                                String hr = jsonResponse.getString("handrid");
                                String rmp = jsonResponse.getString("rampur");
                                String vtr = jsonResponse.getString("vetur");
                                String indr = jsonResponse.getString("innandyra");
                                String drp = jsonResponse.getString("dropp");
                                String upl = jsonResponse.getString("upplyst");
                                String cin = jsonResponse.getString("checkedIn");

                                intent.putExtra("nafn", nfn);
                                intent.putExtra("lysing", lsng);
                                intent.putExtra("lat", lat);
                                intent.putExtra("lng", lng);
                                intent.putExtra("troppur", tr);
                                intent.putExtra("handrid", hr);
                                intent.putExtra("rampur", rmp);
                                intent.putExtra("vetur", vtr);
                                intent.putExtra("innandyra", indr);
                                intent.putExtra("dropp", drp);
                                intent.putExtra("upplyst", upl);
                                intent.putExtra("checkedIn", cin);


                                FindSpotActivity.this.startActivity(intent);

                            }else{
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
