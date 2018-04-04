package isbhv2.hi.notandi.skater.service;

/*
Request klasi sem sér um að hafa samskipti við spot töfluna
þegar setja á inn nýjan stað í gagnagrunninn.
 */

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bjarki on 13.2.2018.
 */

public class newSpotRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL= "http://skaterapp.xyz/newSpot.php";
    private Map<String, String> params;

    public newSpotRequest(String nafn, String lysing, String troppur, String handrid, String rampur,
                          String vetur, String innandyra, String dropp, String upplyst, String lat, String lng, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nafn", nafn);
        params.put("lysing", lysing);
        params.put("troppur", troppur);
        params.put("handrid", handrid);
        params.put("rampur", rampur);
        params.put("vetur", vetur);
        params.put("innandyra", innandyra);
        params.put("dropp", dropp);
        params.put("upplyst", upplyst);
        params.put("lat", lat);
        params.put("lng", lng);

        Log.d("myTag", nafn + "  " + lysing + "  " + troppur);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
