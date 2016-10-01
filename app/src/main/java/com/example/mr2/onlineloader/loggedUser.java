package com.example.mr2.onlineloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class loggedUser extends AppCompatActivity {
TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = (TextView)findViewById(R.id.msg);
        setContentView(R.layout.activity_logged_user);


    }
}
