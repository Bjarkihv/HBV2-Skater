package isbhv2.hi.notandi.skater.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Notandi on 27.3.2018.
 */

public class ListParseRequest extends AsyncTask<Void,Integer,Integer> {

    Context c;
    ListView listviewListi;
    String data;

    ArrayList<String> username = new ArrayList<>();
    ArrayList<String> spot = new ArrayList<>();
    ArrayList<String> results = new ArrayList<>();
    ProgressDialog pd;

    public ListParseRequest(Context c, String data, ListView listviewListi){
        this.c = c;
        this.data = data;
        this.listviewListi = listviewListi;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing hlutirnir eru að koma ");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {

        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if(integer == 1) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1, results/*username*/ );
            listviewListi.setAdapter(adapter);


            listviewListi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Snackbar.make(view,username.get(position),Snackbar.LENGTH_SHORT).show();;
                    Snackbar.make(view,spot.get(position),Snackbar.LENGTH_SHORT).show();;
                }
            });
        }else{
            Toast.makeText(c, "Fall nær ekki í gögn, getur ekki parsað  ", Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }

    private int parse(){
        try {
            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;
            username.clear();
            spot.clear();
            results.clear();
            for(int i = 0; i < ja.length(); i++)
            {
                jo=ja.getJSONObject(i);

                String uName=jo.getString("username");
                username.add(uName);
                String uSpot=jo.getString("spot");
                spot.add(uSpot);

                results.add("" + uName + " er staddur á  " + "\n" + uSpot + "i");
                //resultsString = nafn + ":\n" + lysing + "\n" + "Til staðar: " + finFlokkar + "\n" + checkedInString;
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
