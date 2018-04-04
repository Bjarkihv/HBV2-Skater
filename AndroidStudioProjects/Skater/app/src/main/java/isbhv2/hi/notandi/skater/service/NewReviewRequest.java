package isbhv2.hi.notandi.skater.service;

/*
Request klasi sem sér um að hafa samskipti við review töfluna
þegar setja á inn nýja umsögn um stað.
 */

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bjarki on 13.2.2018.
 */

public class NewReviewRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL= "http://skaterapp.xyz/newReview.php";
    private Map<String, String> params;

    public NewReviewRequest(String nafn, String rating, String title, String review, String user, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nafn", nafn);
        params.put("title", title);
        params.put("rating", rating);
        params.put("review", review);
        params.put("user", user);
        Log.d("myTag", rating + "  " + title + "  " + review + " " + user);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
