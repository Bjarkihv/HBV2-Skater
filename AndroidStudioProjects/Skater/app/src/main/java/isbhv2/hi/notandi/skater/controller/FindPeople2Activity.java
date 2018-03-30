package isbhv2.hi.notandi.skater.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.service.CheckedMembers2;

/**
 * Created by Notandi on 14.3.2018.
 */

public class FindPeople2Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        final Button memberList = (Button) findViewById(R.id.button5);

        final String username = "HVAÐ KEMUR";
        final String spot = "EF EITTHVAÐ";

        memberList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                 @Override
                    public void onResponse(String response){
                    try{
                        Log.d("virkar try í list2", "hvað í fjandanum á að gerast. ");
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){
                            Log.d("tenging við gagnagrunn", " tókst");
                            Intent intent = new Intent(FindPeople2Activity.this, FindPeopleActivity.class);

                            String username;
                            String spot;

                            for(int i = 0; i < jsonResponse.length()-1;i++){
                                Log.d("tenging við forlykkju", " tókst");
                                JSONArray jsonArray = jsonResponse.getJSONArray(Integer.toString(i));
                                Log.d("tenging jsonArrayTEST", " tókst");
                                Log.d("jsonArrayTEST :" , jsonArray.toString());

                                username = jsonArray.getString(4);
                                spot = jsonArray.getString(3);


                                Log.d("FindPeople2Activity: ", "þetta er að keyra");

                                intent.putExtra("nafn"+Integer.toString(i),username);
                                intent.putExtra("spot"+Integer.toString(i),spot);

                            }
                            intent.putExtra("length", jsonResponse.length());
                            FindPeople2Activity.this.startActivity(intent);
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindPeople2Activity.this);
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
                CheckedMembers2 checkedMembers2 = new CheckedMembers2(username, spot, "false", responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindPeople2Activity.this);
                queue.add(checkedMembers2);
            }
        });
    }
}
