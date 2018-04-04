package isbhv2.hi.notandi.skater.service;

/*
Request klasi sem sér um að hafa samskipti við user töfluna í
gagnagrunninum til að sjá hvort login credentials passi þegar
notandi reynir að skrá sig inn.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bjarki on 13.2.2018.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL= "http://skaterapp.xyz/login.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
