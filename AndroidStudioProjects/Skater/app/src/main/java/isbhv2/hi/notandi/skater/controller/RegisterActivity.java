package isbhv2.hi.notandi.skater.controller;

/*
Activity sem sér um að senda upplýsingar frá notanda
í RegisterRequest, sem sér svo um að tékka á login credentials
í gagnagrunni og senda svar til baka.
 */

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import isbhv2.hi.notandi.skater.R;
import isbhv2.hi.notandi.skater.service.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean verifyEmail(String s){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(s);
        return matcher.find();
    }

    public boolean verifyUsernameLength(String s){
        if(s.length() < 20)
            return true;
        else
            return false;
    }
    // TODO
    // kannski einhver requirements fyrir sterkt password?
    public boolean verifyPassword(String s){
        if(s.length() < 20)
            return true;
        else
            return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Hvaða XML skrá þetta activity notar

        final EditText regEmail = (EditText) findViewById(R.id.inputEmail);
        final EditText regUsername = (EditText) findViewById(R.id.inputName);
        final EditText regPassword = (EditText) findViewById(R.id.inputPassword);
        final Button bRegister = (Button) findViewById(R.id.registerButton);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = regUsername.getText().toString();
                final String email = regEmail.getText().toString();
                final String password = regPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("myTag", "Response móttekið");

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            // Ef þetta tekst þá sendum við notanda aftur á login síðuna.
                            if(success && verifyEmail(email)){
                                Log.d("myTag", "Intent keyrt");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else if(!verifyEmail(email)){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Netfang er ekki á réttu formi")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }else if(!verifyUsernameLength(username)){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Notendafn má ekki vera lengra en 20 slög")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Póstfang eða notendanafn eru nú þegar í notkun")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(username, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
