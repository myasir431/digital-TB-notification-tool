package com.testing.yasir.userinterface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    EditText name;
    EditText pass;
    EditText cpass;
    Spinner loc;
    Button signupBtn;
    String dname,dpass,dcpass, location;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.Name);
        loc = (Spinner) findViewById(R.id.location);
        pass = (EditText) findViewById(R.id.pass1);
        cpass = (EditText) findViewById(R.id.cpass);
        signupBtn = (Button) findViewById(R.id.ButtonSignup);

        adapter = ArrayAdapter.createFromResource(this, R.array.district, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loc.setAdapter(adapter);
        loc.setSelection(0);
        loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    location = loc.getSelectedItem().toString();

                    //gendr = (gendr).findViewById(e);
                    //Log.d("gender: ", SpinnerDiseaseType.toString());
                    signupBtn.setEnabled(true);

                } else {
                    signupBtn.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    public void onSignupClick(View v)
    {
        if (v.getId() == R.id.ButtonSignup) {
            dname = name.getText().toString();
            dpass = pass.getText().toString();
            dcpass = cpass.getText().toString();


            if (!dpass.equals(dcpass)) {
                //pop up message.
                Toast pass = Toast.makeText(signup.this, "Password don't match.", Toast.LENGTH_SHORT);
                pass.show();
            } else {
                // insert data into database.
                connectDB con = new connectDB();
                con.setName(dname);
                con.setPassword(dpass);
                con.setLocation(location);

                helper.insertUser(con);
            }

            Intent intent=new Intent(this, login.class);
            startActivity(intent);
        }

    }
}
