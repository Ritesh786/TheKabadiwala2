package com.newkabadiwala.fujitsu.thekabadiwala;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SendOtp extends AppCompatActivity implements View.OnClickListener {

    private EditText verifyotptext;


    private AppCompatButton sendotpbtn;
    String motptext;

    String usertype = "otp";

    UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        session = new UserSessionManager(getApplicationContext());

        verifyotptext = (EditText) findViewById(R.id.otp_veryfy);


        sendotpbtn = (AppCompatButton) findViewById(R.id.veryfyotp_btn);

        sendotpbtn.setOnClickListener(this);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {

        loginviaotp();

    }

    private void loginviaotp() {

        Intent intent = getIntent();
    String  mobilenumber = intent.getStringExtra("mobileno");


        motptext = verifyotptext.getText().toString().trim();

        //http://kabadiwalatest.azurewebsites.net/api/Member/Login?username={username or mobile}&password={otp}&logintype=otp

        //     final String REGISTER_URL = "http://kabadiwalatest.azurewebsites.net/api/member/register?username=" + usernsme + "&password=" + password + "&area=" + area + "&city=" + city + "&landmark=" + landmark + "&state=" + state + "&pincode=" + pincode + "&name=" + name + "&mobileno=" + mobile + "&email=" + email + "&usertype=" + usertype + "";
        final String LOGIN_URL ="http://kabadiwalatest.azurewebsites.net/api/Member/Login?username="+mobilenumber+"&password="+motptext+"&logintype="+usertype+"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("jaba",motptext);
                        try {
                            JSONObject jsonresponse = new JSONObject(response);
                            boolean success = jsonresponse.getBoolean("success");

                            if(success){

                                String name = jsonresponse.getString("name");

                                session.createUserLoginSession(name);

                                Intent sendotpintent = new Intent(SendOtp.this,DashBoard.class);
                                sendotpintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Add new Flag to start new Activity
                                sendotpintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(sendotpintent);
                                finish();


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(SendOtp.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(SendOtp.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("jabadi",motptext);
                        Toast.makeText(SendOtp.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {

        };RequestQueue requestQueue = Volley.newRequestQueue(SendOtp.this);
        requestQueue.add(stringRequest);
    }

}
