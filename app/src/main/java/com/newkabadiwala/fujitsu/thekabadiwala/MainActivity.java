package com.newkabadiwala.fujitsu.thekabadiwala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.newkabadiwala.fujitsu.thekabadiwala.activity.SmsActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mloginotp,mloginbtn,mforgotbtn,msignupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mloginotp = (Button) findViewById(R.id.loginotp_btn);
        mloginbtn = (Button) findViewById(R.id.login_btn);
        mforgotbtn = (Button) findViewById(R.id.forgotpass_btn);
        msignupbtn = (Button) findViewById(R.id.signup_btn);

        mloginotp.setOnClickListener(this);
        mloginbtn.setOnClickListener(this);
        mforgotbtn.setOnClickListener(this);
        msignupbtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent (MainActivity.this, LoginViaOtp.class);
        startActivity(intent);

    }
}
