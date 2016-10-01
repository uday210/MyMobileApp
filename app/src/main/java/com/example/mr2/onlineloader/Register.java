package com.example.mr2.onlineloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    EditText name,surname,age,user_name,password;
    String str_name,str_sname,str_age,str_uname,str_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.uname);
        surname =(EditText)findViewById(R.id.sname);
        age = (EditText)findViewById(R.id.age);
        user_name = (EditText)findViewById(R.id.user_name);
        password  =(EditText)findViewById(R.id.pass);

    }


    public void OnReg(View view){
        str_name = name.getText().toString();
        str_sname = surname.getText().toString();
        str_age = age.getText().toString();
        str_uname = user_name.getText().toString();
        str_pass = password.getText().toString();

        String type = "register";

        backroundWorker bw = new backroundWorker(this);
       bw.execute(type,str_name,str_sname,str_age,str_uname,str_pass);




    }
}
