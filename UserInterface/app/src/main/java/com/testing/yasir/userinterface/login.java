package com.testing.yasir.userinterface;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class login extends AppCompatActivity {

    public static String  PREFS_NAME="mypre";
    public static String PREF_USERNAME="username";
    public static String PREF_PASSWORD="password";
    DBHelper helper = new DBHelper(this);
    EditText txtuser,txtpwd;
    CheckBox ch;
    String name,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onButtonclick(View v)
    {
        if(v.getId()==R.id.Blogin)
        {
            txtuser=(EditText)findViewById(R.id.uname);
            txtpwd=(EditText)findViewById(R.id.password);
            ch=(CheckBox)findViewById(R.id.checkme);

            name = txtuser.getText().toString();
            pwd = txtpwd.getText().toString();

            //Search query method in DBHelper class
            String password = helper.searchpass(name);

            if(pwd.equals(password))
            {
                if(ch.isChecked()) {
                    rememberMe(name, pwd);
                    showActivity(name);
                }
                else
                {
                    ch.setError("Click Here.");
                }
            }
            else
            {
                Toast.makeText(login.this,"Username and Password don't match.",Toast.LENGTH_SHORT).show();
            }
        }
        // if signup clicked then goto sign up activity.
        if(v.getId()==R.id.Bsignup)
        {
            Intent intent=new Intent(this, signup.class);
            startActivity(intent);
        }
    }

    public void onStart(){
        super.onStart();
        //read username and password from SharedPreferences
        getUser();
    }

    public void getUser(){
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            //directly show menu
            showActivity(username);
        }
    }




    public void rememberMe(String user, String pasword){
        //save username and password in SharedPreferences
        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME,user)
                .putString(PREF_PASSWORD,pasword)
                .commit();
    }



    public void showActivity(String username){
        //display log out activity
        Intent intent=new Intent(this, UserInterface.class);
        intent.putExtra("user",username);
        startActivity(intent);
    }
}
