package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        final EditText searchInputString = (EditText) findViewById(R.id.searchInput);
        final Button bSearch = (Button) findViewById(R.id.searchButton);


        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = searchInputString.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Log.d("myTag", "Success");
                                String description = jsonResponse.getString("description");

                                Intent intent = new Intent(FindSpotActivity.this, ResultsActivity.class);
                                intent.putExtra("description", description);

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

                SearchRequest searchRequest = new SearchRequest(name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindSpotActivity.this);
                queue.add(searchRequest);
            }
        });


    }

}
