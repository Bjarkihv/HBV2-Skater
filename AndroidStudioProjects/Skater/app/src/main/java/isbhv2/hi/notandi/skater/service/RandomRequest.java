package isbhv2.hi.notandi.skater.service;

/*
Request klasi sem sér um að hafa samskipti við spot töfluna
þegar notandi leitar að stað eftir sérstökur skilyrðum.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bjarki on 13.2.2018.
 */

public class RandomRequest extends StringRequest {
    private static final String SEARCH_REQUEST_URL= "http://skaterapp.xyz/randomSpot.php";
    private Map<String, String> params;

    public RandomRequest(String troppur, String handrid, String rampur,
                         String vetur, String innandyra, String dropp, String upplyst, Response.Listener<String> listener){
        super(Method.POST, SEARCH_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("troppur", troppur);
        params.put("handrid", handrid);
        params.put("rampur", rampur);
        params.put("vetur", vetur);
        params.put("innandyra", innandyra);
        params.put("dropp", dropp);
        params.put("upplyst", upplyst);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
