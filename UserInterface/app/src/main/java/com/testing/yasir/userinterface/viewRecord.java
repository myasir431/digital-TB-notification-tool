package com.testing.yasir.userinterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class viewRecord extends AppCompatActivity {

    public static String  PREFS_NAME="Message";

    ArrayAdapter<String> arrayAdapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        lv = (ListView) findViewById(R.id.smslist);




        SharedPreferences sp = getSharedPreferences("Message",MODE_PRIVATE);
        String message=sp.getString("detail","");
        Log.d("message",message);
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

        String[] msgs = new String[]{message};
        List<String> arrayList = new ArrayList<String>(Arrays.asList(msgs));

        //ArrayList<String> arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(arrayAdapter);
        arrayList.add(message);
        arrayAdapter.notifyDataSetChanged();




    }


    public void onClick(View v){
        Intent in = new Intent(this,UserInterface.class);
        startActivity(in);
        finish();
    }
}
