package com.newkabadiwala.fujitsu.thekabadiwala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Success extends AppCompatActivity implements View.OnClickListener {

    TextView nameuser;
    Button mlogout;
    // User Session Manager Class
    UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);


        // Session class instance
        session = new UserSessionManager(getApplicationContext());


        nameuser = (TextView) findViewById(R.id.name_of_user);
        mlogout = (Button) findViewById(R.id.logout_btn);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if(session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        String name = user.get(UserSessionManager.KEY_NAME);

        // get email

        // Show user data on activity
        nameuser.setText(name);

        mlogout.setOnClickListener(this);


//        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");
//
//        nameuser.setText(name);

    }

    @Override
    public void onClick(View v) {

        session.logoutUser();
        finish();
    }
}
