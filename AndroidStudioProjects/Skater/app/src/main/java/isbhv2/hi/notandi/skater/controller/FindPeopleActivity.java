package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import isbhv2.hi.notandi.skater.InfoMapsActivity;
import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.service.CheckInOutRequest;
import isbhv2.hi.notandi.skater.service.CheckedMembers;

import static isbhv2.hi.notandi.skater.controller.LoginActivity.currentUser;

public class FindPeopleActivity extends AppCompatActivity {

    String resultsString;
    String username;
    String spot;

    String usernameIndex[] = new String[100];
    String spotIndex[] = new String[100];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);
        Intent intent  = getIntent();
        int len = intent.getIntExtra("length",0)-1;

        String resultsList[] = new String[len];


        Log.d("Len", Integer.toString(len));
        ListView results;

        for(int i = 0; i < len; i++){
            username = intent.getStringExtra("nafn" + Integer.toString(i));
            spot = intent.getStringExtra("spot" + Integer.toString(i));

            usernameIndex[i] = username;
            spotIndex[i] = spot;


            resultsString = username + ":\n" + spot;
            resultsList[i] = resultsString;

            Log.d("Bara að prufa að logga", "hvað á að gerast. ");
            Log.d("ResListi: ", "i: " + i + " ->" + resultsList[0] + " " + resultsList[1] + " " + resultsList[2] + " " + resultsList[3]);
        }

        Log.d("Cont: ", Integer.toString(resultsList.length));
        results = (ListView) findViewById(R.id.peopleList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_people_list_show, R.id.textViewPeople, resultsList);
        results.setAdapter(arrayAdapter);


        //CheckedMembers checkedMembers = new CheckedMembers(username, spot);
        //RequestQueue queue = Volley.newRequestQueue(FindPeopleActivity.this);
        //queue.add(checkedMembers);


    }
}
