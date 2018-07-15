package com.testing.yasir.smsreceiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    String urlAddress = "http://172.1.20.252/patient/insertapi.php";
   // EditText tvMessage;
   // EditText tvNumber;

    TextView tv;
    //IntentFilter intentFilter;

    BroadcastReceiver myBroadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String msg = getIntent().getStringExtra("sms");
        Toast.makeText(getApplication(),msg,Toast.LENGTH_SHORT);
        tv =(TextView) findViewById(R.id.textMsg);
        tv.setText(msg);
        Postman p = new Postman(MainActivity.this, urlAddress,msg);
        p.execute();
    }
  /*  @Override
    protected void onResume(){
        registerReceiver(intentReceiver,intentFilter);
        super.onResume();
    }

    protected void onPause(){
        unregisterReceiver(intentReceiver);
        super.onPause();
    }*/
}

