package isbhv2.hi.notandi.skater.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Notandi on 27.3.2018.
 */

public class ListDownloadRequest extends AsyncTask<Void,Integer,String> {


    Context c;
    String address;
    ListView listviewListi;

    ProgressDialog pd;
    public ListDownloadRequest(Context c, String address, ListView listviewListi){
        this.c = c;
        this.address = address;
        this.listviewListi = listviewListi;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Ná í gögn");
        pd.setMessage("Nær í gögn, vinsamlegast bíðið");
        pd.show();
    }


    @Override
    protected String doInBackground(Void... params){
        String data=downloadListData();
        return data;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();

        if(s != null){
            ListParseRequest p = new ListParseRequest(c,s,listviewListi);
            p.execute();

        }else {
            Toast.makeText(c, "Gat ekki náð í gögn", Toast.LENGTH_SHORT).show();
        }

    }

    private String downloadListData(){
        //tengjast við gögnin og streyma þau yfir í appið.
        InputStream is=null;
        String line =null;

        try{
            URL url = new URL(address);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            is= new BufferedInputStream(connect.getInputStream());

            BufferedReader br=new BufferedReader(new InputStreamReader(is));

            StringBuffer sb=new StringBuffer();

            if(br != null)
            {
                while((line=br.readLine())!= null)
                {
                    sb.append(line+"\n");
                }
            }else{
                return null;
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {

                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
