package com.newkabadiwala.fujitsu.thekabadiwala;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mloginotp, mloginbtn, mforgotbtn, msignupbtn;
    EditText musername,mpassword;
    String usernsme,password;
    String usertype = "pass";

    UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        mloginotp = (Button) findViewById(R.id.loginotp_btn);
        mloginbtn = (Button) findViewById(R.id.login_btn);
        mforgotbtn = (Button) findViewById(R.id.forgotpass_btn);
        msignupbtn = (Button) findViewById(R.id.signup_btn);

        musername = (EditText) findViewById(R.id.login_username);
        mpassword = (EditText) findViewById(R.id.login_password);


        mloginotp.setOnClickListener(this);
        mloginbtn.setOnClickListener(this);
        mforgotbtn.setOnClickListener(this);
        msignupbtn.setOnClickListener(this);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login_btn:

                loginuser();

                break;


            case R.id.loginotp_btn:

                Intent intent = new Intent(MainActivity.this, LoginViaOtp.class);
                startActivity(intent);
                MainActivity.this.finish();

                break;

            case R.id.signup_btn:

                Intent signintent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(signintent);

                break;

            default:
                break;


        }
    }


    private void loginuser() {

        usernsme = musername.getText().toString().trim();
        password = mpassword.getText().toString().trim();

      //  http://kabadiwalatest.azurewebsites.net/api/Member/Login?username={username or mobile}&password={password}&logintype=pass

   //     final String REGISTER_URL = "http://kabadiwalatest.azurewebsites.net/api/member/register?username=" + usernsme + "&password=" + password + "&area=" + area + "&city=" + city + "&landmark=" + landmark + "&state=" + state + "&pincode=" + pincode + "&name=" + name + "&mobileno=" + mobile + "&email=" + email + "&usertype=" + usertype + "";
        final String LOGIN_URL ="http://kabadiwalatest.azurewebsites.net/api/Member/Login?username="+usernsme+"&password="+password+"&logintype="+usertype+"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("jaba",usernsme);
                        try {
                            JSONObject jsonresponse = new JSONObject(response);
                            boolean success = jsonresponse.getBoolean("success");

                            if(success){

                                String name = jsonresponse.getString("name");

                                session.createUserLoginSession(name);

                                Intent registerintent = new Intent(MainActivity.this,DashBoard.class);
                                registerintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Add new Flag to start new Activity
                                registerintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(registerintent);
                                finish();


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("jabadi",usernsme);
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {

        };RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

}