package com.newkabadiwala.fujitsu.thekabadiwala;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginViaOtp extends AppCompatActivity implements View.OnClickListener {


    private EditText sendotptext;


    private AppCompatButton sendotpbtn;
    String motptext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_via_otp);


        sendotptext = (EditText) findViewById(R.id.mobileotp);


        sendotpbtn = (AppCompatButton) findViewById(R.id.sendotp_btn);

        sendotpbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        loginviaotp();
    }

    private void loginviaotp() {

        motptext = sendotptext.getText().toString().trim();

        //  http://kabadiwalatest.azurewebsites.net/api/Member/Login?username={username or mobile}&password={password}&logintype=pass

        //     final String REGISTER_URL = "http://kabadiwalatest.azurewebsites.net/api/member/register?username=" + usernsme + "&password=" + password + "&area=" + area + "&city=" + city + "&landmark=" + landmark + "&state=" + state + "&pincode=" + pincode + "&name=" + name + "&mobileno=" + mobile + "&email=" + email + "&usertype=" + usertype + "";
        final String LOGIN_URL ="http://kabadiwalatest.azurewebsites.net/api/Member/LoginOtp?username="+motptext+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("jaba",motptext);
                        try {
                            JSONObject jsonresponse = new JSONObject(response);
                            boolean success = jsonresponse.getBoolean("success");

                            if(success){

                                Intent loginviaotpintent = new Intent(LoginViaOtp.this,SendOtp.class);
                                loginviaotpintent.putExtra("mobileno",motptext);

                                startActivity(loginviaotpintent);
                                finish();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginViaOtp.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(LoginViaOtp.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("jabadi",motptext);
                        Toast.makeText(LoginViaOtp.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {

        };RequestQueue requestQueue = Volley.newRequestQueue(LoginViaOtp.this);
        requestQueue.add(stringRequest);
    }


}
