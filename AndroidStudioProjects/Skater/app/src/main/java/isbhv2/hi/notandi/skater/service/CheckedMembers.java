package isbhv2.hi.notandi.skater.service;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Indriði on 14.3.2018.
 */

public class CheckedMembers extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://skaterapp.xyz/checkedMembers.php";
    private Map<String, String> params;

        //Log.d("CheckedMembers:" , "Þetta fall fékk kall fyrir hrun");

    public CheckedMembers(String username, String spot){
        super(Method.POST, LOGIN_REQUEST_URL, null, null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("spot",spot);

        Log.d( "CheckedMembers: " , "Þetta fall fékk kall eftir hrun");
    }


    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
