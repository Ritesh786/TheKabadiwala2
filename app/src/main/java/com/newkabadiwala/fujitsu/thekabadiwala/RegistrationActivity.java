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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText musertype, musername, mpassword, mname, memail, mmobile, mcity, marea, mstate, mlandmark, mpincode;
    Button mbtnregister;

    String usertype, usernsme, password, name, email, mobile, city, area, state, landmark, pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        musertype = (EditText) findViewById(R.id.reg_usertype);
        musername = (EditText) findViewById(R.id.reg_username);
        mpassword = (EditText) findViewById(R.id.reg_password);
        mname = (EditText) findViewById(R.id.reg_name);
        memail = (EditText) findViewById(R.id.reg_email);
       // mmobile = (EditText) findViewById(R.id.reg_mobile);
        mcity = (EditText) findViewById(R.id.reg_city);
        marea = (EditText) findViewById(R.id.reg_area);
        mstate = (EditText) findViewById(R.id.reg_state);
        mlandmark = (EditText) findViewById(R.id.reg_landmark);
        mpincode = (EditText) findViewById(R.id.reg_pincode);





        mbtnregister = (Button) findViewById(R.id.btn_Register);
        mbtnregister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        registerUser();
    }

    private void registerUser() {

        usertype = musertype.getText().toString().trim();
        usernsme = musername.getText().toString().trim();
        password = mpassword.getText().toString().trim();
        name = mname.getText().toString().trim();
        email = memail.getText().toString().trim();
    //    mobile = mmobile.getText().toString().trim();
        city = mcity.getText().toString().trim();
        area = marea.getText().toString().trim();
        state = mstate.getText().toString().trim();
        landmark = mlandmark.getText().toString().trim();
        pincode = mpincode.getText().toString().trim();

        final String REGISTER_URL = "http://kabadiwalatest.azurewebsites.net/api/member/register?username=" + usernsme + "&password=" + password + "&area=" + area + "&city=" + city + "&landmark=" + landmark + "&state=" + state + "&pincode=" + pincode + "&name=" + name + "&mobileno=" + usernsme + "&email=" + email + "&usertype=" + usertype + "";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("jaba",usernsme);
                        try {
                            JSONObject jsonresponse = new JSONObject(response);
                            boolean success = jsonresponse.getBoolean("success");

                            if(success){

                                Intent registerintent = new Intent(RegistrationActivity.this,MainActivity.class);
                                startActivity(registerintent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                builder.setMessage("Registration Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(RegistrationActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("jabadi",usernsme);
                        Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {

        };RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
          requestQueue.add(stringRequest);
    }
}

