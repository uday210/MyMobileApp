package com.example.mr2.onlineloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    EditText username1,userpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username1 = (EditText)findViewById(R.id.editText);
        userpassword = (EditText)findViewById(R.id.editText2);
    }


    public void OnLogin(View view){

        String uname = username1.getText().toString();
        String pass  = userpassword.getText().toString();
        String type = "Login";
        backroundWorker bw = new backroundWorker(this);
        bw.execute(type,uname,pass);
    }

    public void OnSup(View view){
        startActivity(new Intent(this,Register.class));


    }
}
