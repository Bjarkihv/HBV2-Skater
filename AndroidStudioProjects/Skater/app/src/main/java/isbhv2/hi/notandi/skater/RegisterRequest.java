package isbhv2.hi.notandi.skater;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bjarki on 13.2.2018.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL= "http://10.0.2.2/register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String email, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);
        Log.d("myTag", username + "  " + email + "  " + password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
