package isbhv2.hi.notandi.skater.controller;

/*
Activity sem sér um að ná í allar umsagnir um stað í gegnum
GetReviewsRequest, og birtir þær síðan til notanda.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import isbhv2.hi.notandi.skater.InfoMapsActivity;
import isbhv2.hi.notandi.skater.R;

public class ReviewsActivity extends AppCompatActivity {
    String resultsString;
    String nafn;
    String rating;
    String title;
    String review;
    String user;

    String ratingIndex[] = new String[100];
    String titleIndex[] = new String[100];
    String reviewIndex[] = new String[100];
    String userIndex[] = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        int len = intent.getIntExtra("length", 0)-1;
        String resultsList[] = new String[len];


        Log.d("Len ", Integer.toString(len));
        ListView results;

        for(int i = 0; i < len; i++){

            rating = intent.getStringExtra("rating"+Integer.toString(i));
            title = intent.getStringExtra("title"+Integer.toString(i));
            review = intent.getStringExtra("review"+Integer.toString(i));
            user = intent.getStringExtra("user"+Integer.toString(i));

            ratingIndex[i] = rating;
            titleIndex[i] = title;
            reviewIndex[i] = review;
            userIndex[i] = user;

            resultsString = title + ":\n" + review + "\n" + "Einkunn: " + rating + "/5.0\n" + "Notandi: " + user;
            resultsList[i] = resultsString;
        }
        Log.d("Cont: ", Integer.toString(resultsList.length));
        results = (ListView) findViewById(R.id.resultList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView, resultsList);
        results.setAdapter(arrayAdapter);

        /*
        results.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(ReviewsActivity.this, InfoMapsActivity.class);
                intent2.putExtra("nafn", nafnIndex[(int)id]);
                intent2.putExtra("lysing", lysingIndex[(int)id]);
                intent2.putExtra("flokkar", flokkarIndex[(int)id]);
                intent2.putExtra("lat", latIndex[(int)id]);
                intent2.putExtra("lng", lngIndex[(int)id]);
                ReviewsActivity.this.startActivity(intent2);
            }
        });
        */

    }
}