package isbhv2.hi.notandi.skater.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.service.ListDownloadRequest;

/**
 * Created by Notandi on 27.3.2018.
 */

public class FindPeopleListActivity extends AppCompatActivity {


    String url = "http://skaterapp.xyz/checkedMembers2.php/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list_new_show); //Þetta er nýjasti listinn.


        final ListView listViewListi = (ListView) findViewById(R.id.listviewListi);
        final ListDownloadRequest dll = new ListDownloadRequest(this, url, listViewListi);


        dll.execute();
    }
}
