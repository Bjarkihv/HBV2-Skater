package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.service.NewReviewRequest;
import isbhv2.hi.notandi.skater.service.RegisterRequest;

public class NewReviewActivity extends AppCompatActivity {

    /* TODO
    * Gera verification snyrtilegra og
    * error messages skýrari.
    */
    public boolean verifyTitle(String s){
        if(s.length() < 20)
            return true;
        else
            return false;
    }

    public boolean verifyReview(String s){
        if(s.length() < 200)
            return true;
        else
            return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        final Intent intent = getIntent();

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final EditText reviewTitle = (EditText) findViewById(R.id.reviewTitle);
        final EditText reviewText = (EditText) findViewById(R.id.reviewText);
        final TextView reviewFor = (TextView) findViewById(R.id.reviewFor);
        final Button bSend = (Button) findViewById(R.id.bSend);

        reviewFor.setText("Umsögn fyrir: " + intent.getStringExtra("nafn"));

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final double rat = ratingBar.getRating();
                final String rating = Double.toString(rat);
                final String title = reviewTitle.getText().toString();
                final String review = reviewText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("myTag", "Response móttekið");

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            // Ef þetta tekst þá sendum við notanda aftur á login síðuna.
                            if(success && verifyTitle(title) && verifyReview(review)){
                                Log.d("myTag", "Intent keyrt");
                                Intent intent = new Intent(NewReviewActivity.this, UserAreaActivity.class);
                                NewReviewActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewReviewActivity.this);
                                builder.setMessage("Skráning umsagnar tókst ekki")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                NewReviewRequest newReviewRequest = new NewReviewRequest(intent.getStringExtra("nafn"), rating, title, review, LoginActivity.currentUser.getUsername(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(NewReviewActivity.this);
                queue.add(newReviewRequest);
            }
        });
    }
}
