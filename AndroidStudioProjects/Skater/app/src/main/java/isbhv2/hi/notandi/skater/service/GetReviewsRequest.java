package isbhv2.hi.notandi.skater.service;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bjarki on 13.2.2018.
 */

public class GetReviewsRequest extends StringRequest {
    private static final String SEARCH_REQUEST_URL= "http://skaterapp.xyz/getReviews.php";
    private Map<String, String> params;

    public GetReviewsRequest(String nafn, Response.Listener<String> listener){
        super(Method.POST, SEARCH_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nafn", nafn);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
