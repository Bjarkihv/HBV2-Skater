package isbhv2.hi.notandi.skater.service;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bjarki on 13.2.2018.
 */

public class SearchRequest extends StringRequest {
    private static final String SEARCH_REQUEST_URL= "http://10.0.2.2/spots.php";
    private Map<String, String> params;

    public SearchRequest(String name, Response.Listener<String> listener){
        super(Method.POST, SEARCH_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
