package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import isbhv2.hi.notandi.skater.InfoMapsActivity;
import isbhv2.hi.notandi.skater.R;

public class ResultsActivity extends AppCompatActivity {
    String resultsString;
    String nafn;
    String lysing;
    String lat;
    String lng;
    String troppur;
    String handrid;
    String rampur;
    String vetur;
    String innandyra;
    String dropp;
    String upplyst;
    String checkedIn;
    String finFlokkar;

    String nafnIndex[] = new String[100];
    String lysingIndex[] = new String[100];
    String flokkarIndex[] = new String[100];
    String latIndex[] = new String[100];
    String lngIndex[] = new String[100];

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

            nafn = intent.getStringExtra("nafn"+Integer.toString(i));
            lysing = intent.getStringExtra("lysing"+Integer.toString(i));
            lat = intent.getStringExtra("lat"+Integer.toString(i));
            lng = intent.getStringExtra("lng"+Integer.toString(i));
            troppur = intent.getStringExtra("troppur"+Integer.toString(i));
            handrid = intent.getStringExtra("handrid"+Integer.toString(i));
            rampur = intent.getStringExtra("rampur"+Integer.toString(i));
            vetur = intent.getStringExtra("vetur"+Integer.toString(i));
            innandyra = intent.getStringExtra("innandyra"+Integer.toString(i));
            dropp = intent.getStringExtra("dropp"+Integer.toString(i));
            upplyst = intent.getStringExtra("upplyst"+Integer.toString(i));
            checkedIn = intent.getStringExtra("checkedIn"+Integer.toString(i));

            String flokkar = "- ";
            if (troppur.equals("true")) flokkar += "tröppur - ";
            if (handrid.equals("true")) flokkar += "handrið - ";
            if (rampur.equals("true")) flokkar += "rampur - ";
            if (vetur.equals("true")) flokkar += "vetrarvænt - ";
            if (innandyra.equals("true")) flokkar += "innandyra - ";
            if (dropp.equals("true")) flokkar += "drop - ";
            if (upplyst.equals("true")) flokkar += "upplýst - ";

            finFlokkar = flokkar;
            String checkedInString = checkedIn + " notendur tékkaðir inn.";

            nafnIndex[i] = nafn;
            lysingIndex[i] = lysing;
            flokkarIndex[i] = finFlokkar;
            latIndex[i] = lat;
            lngIndex[i] = lng;

            if (checkedIn.equals("0"))
                checkedInString = "Enginn notandi tékkaður inn.";
            if (checkedIn.equals("1"))
                checkedInString = "1 notandi tékkaður inn.";

            resultsString = nafn + ":\n" + lysing + "\n" + "Til staðar: " + finFlokkar + "\n" + checkedInString;
            resultsList[i] = resultsString;
            Log.d("ResListi: ", "i: " + i + " ->" + resultsList[0] + " " + resultsList[1] + " " + resultsList[2] + " " + resultsList[3]);
    }
        Log.d("Cont: ", Integer.toString(resultsList.length));
        results = (ListView) findViewById(R.id.resultList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView, resultsList);
        results.setAdapter(arrayAdapter);


        results.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(ResultsActivity.this, InfoMapsActivity.class);
                intent2.putExtra("nafn", nafnIndex[(int)id]);
                intent2.putExtra("lysing", lysingIndex[(int)id]);
                intent2.putExtra("flokkar", flokkarIndex[(int)id]);
                intent2.putExtra("lat", latIndex[(int)id]);
                intent2.putExtra("lng", lngIndex[(int)id]);
                ResultsActivity.this.startActivity(intent2);
            }
        });

    }
}
