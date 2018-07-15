package com.testing.yasir.userinterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static com.testing.yasir.userinterface.login.PREFS_NAME;
import static com.testing.yasir.userinterface.login.PREF_USERNAME;

public class UserInterface extends AppCompatActivity {

    LinearLayout addPatient;
    LinearLayout viewPatient;
    LinearLayout logout;
    String  user,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        addPatient = (LinearLayout) findViewById(R.id.addPatient);
        viewPatient = (LinearLayout) findViewById(R.id.viewPatient);
        logout =(LinearLayout) findViewById(R.id.signout);

        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        username = pref.getString(PREF_USERNAME, null);

        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(UserInterface.this,addRecord.class);
                add.putExtra("user",username);
                startActivity(add);
                //finish();
            }
        });

        viewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sent = new Intent(UserInterface.this,viewRecord.class);
                startActivity(sent);
                //finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ilogout(view);
            }
        });
    }

    public void ilogout(View view){
        SharedPreferences sharedPrefs =getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
        user="";
        //show login form
        Intent intent=new Intent(this, login.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
        moveTaskToBack(true);
    }
}
//android:entries="@array/category"