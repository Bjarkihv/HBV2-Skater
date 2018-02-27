package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import isbhv2.hi.notandi.skater.InfoMapsActivity;
import isbhv2.hi.notandi.skater.R;

public class ResultsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //final ListView results = (ListView) findViewById(R.id.resultList);

        Intent intent = getIntent();
        final String nafn = intent.getStringExtra("nafn");
        final String lysing = intent.getStringExtra("lysing");
        final String lat = intent.getStringExtra("lat");
        final String lng = intent.getStringExtra("lng");
        final String troppur = intent.getStringExtra("troppur");
        final String handrid = intent.getStringExtra("handrid");
        final String rampur = intent.getStringExtra("rampur");
        final String vetur = intent.getStringExtra("vetur");
        final String innandyra = intent.getStringExtra("innandyra");
        final String dropp = intent.getStringExtra("dropp");
        final String upplyst = intent.getStringExtra("upplyst");

        String flokkar = "";
        if(troppur.equals("true")) flokkar += "tröppur, ";
        if(handrid.equals("true")) flokkar += "handrið, ";
        if(rampur.equals("true")) flokkar += "rampur, ";
        if(vetur.equals("true")) flokkar += "vetrarvænt, ";
        if(innandyra.equals("true")) flokkar += "innandyra, ";
        if(dropp.equals("true")) flokkar += "drop, ";
        if(upplyst.equals("true")) flokkar += "upplýst, ";

        ListView results;
        String resultsList[] = {nafn + ":\n" + lysing + "\n" + "Til staðar: " + flokkar};

        results = (ListView) findViewById(R.id.resultList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView, resultsList);
        results.setAdapter(arrayAdapter);

        results.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(ResultsActivity.this, InfoMapsActivity.class);
                intent2.putExtra("nafn", nafn);
                intent2.putExtra("username", lysing);
                intent2.putExtra("lat", lat);
                intent2.putExtra("lng", lng);
                ResultsActivity.this.startActivity(intent2);
            }
        });

    }
}
