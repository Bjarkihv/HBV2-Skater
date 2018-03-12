package isbhv2.hi.notandi.skater.service;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bjarki on 13.2.2018.
 */

public class CheckInOutRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL= "http://skaterapp.xyz/checkinoutspot.php";
    private Map<String, String> params;

    public CheckInOutRequest(String spot, String nafn, String dec, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", nafn);
        params.put("spot", spot);
        params.put("dec", dec);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
