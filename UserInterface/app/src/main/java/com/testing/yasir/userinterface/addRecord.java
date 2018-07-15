package com.testing.yasir.userinterface;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.testing.yasir.userinterface.login.PREF_PASSWORD;
import static com.testing.yasir.userinterface.login.PREF_USERNAME;

public class addRecord extends AppCompatActivity {
    String fname, lname, age, date,user;
    String Phone = "+923316802560";
    int genderPosition,LocationPosition,DiseasePosition;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    EditText TextFname, TextlastName, TextAge;
    Spinner SpinnerGender, SpinnerLocation, SpinnerDiseaseType;
    Button sendSMS;
    ArrayAdapter<CharSequence> adapter;

    List<String> message = new ArrayList<>();
    public static String  PREFS_NAME="Message";
    public static String PREF_USERNAME="detail";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // GEtting value from sharedpreferences
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        user=b.getString("user");

        TextFname = (EditText) findViewById(R.id.EditTextFName);
        TextlastName = (EditText) findViewById(R.id.EditTextLName);
        TextAge = (EditText) findViewById(R.id.EditTextAge);

        SpinnerGender = (Spinner) findViewById(R.id.gender);
        SpinnerLocation = (Spinner) findViewById(R.id.location);
        SpinnerDiseaseType = (Spinner) findViewById(R.id.disease);




        sendSMS = (Button) findViewById(R.id.ButtonSendData);

        adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Log.d("adapter:  ", adapter.toString());
        SpinnerGender.setAdapter(adapter);
        SpinnerGender.setSelection(0);
        SpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    genderPosition = SpinnerGender.getSelectedItemPosition();
                    sendSMS.setEnabled(false);
                } else {


                    //gendr = (gendr).findViewById(e);
                    //Log.d("gender: ", SpinnerGender.toString());
                    sendSMS.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        adapter = ArrayAdapter.createFromResource(this, R.array.district, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerLocation.setAdapter(adapter);
        SpinnerLocation.setSelection(0);
        SpinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    LocationPosition = SpinnerLocation.getSelectedItemPosition();
                    sendSMS.setEnabled(false);
                } else {


                    //gendr = (gendr).findViewById(e);
                    //Log.d("gender: ", SpinnerLocation.toString());
                    sendSMS.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        adapter = ArrayAdapter.createFromResource(this, R.array.disease, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerDiseaseType.setAdapter(adapter);
        SpinnerDiseaseType.setSelection(0);
        SpinnerDiseaseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    DiseasePosition = SpinnerDiseaseType.getSelectedItemPosition();

                    //gendr = (gendr).findViewById(e);
                    //Log.d("gender: ", SpinnerDiseaseType.toString());
                    sendSMS.setEnabled(true);

                } else {
                    sendSMS.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
// send sms code
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else{
            // do nothing.
        }



        date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(new Date());
        try {
            sendSMS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    fname = TextFname.getText().toString();
                    lname = TextlastName.getText().toString();
                    age = TextAge.getText().toString();
                    genderPosition = SpinnerGender.getSelectedItemPosition();
                    LocationPosition = SpinnerLocation.getSelectedItemPosition();
                    DiseasePosition = SpinnerDiseaseType.getSelectedItemPosition();

                    if (TextUtils.isEmpty(fname)) {
                        TextFname.setError("First Name is missing.");
                        return;
                    }
                    if (TextUtils.isEmpty(lname)) {
                        TextFname.setError("Last Name is missing.");
                        return;
                    }
                    if (TextUtils.isEmpty(age)) {
                        TextFname.setError("Age is missing.");
                        return;
                    }

                    else {

                        String Message = user + ","+ date +"," + fname + "," + lname + "," + age + "," + genderPosition + "," + LocationPosition + "," + DiseasePosition;
                        String data = "Date :"+ date +"\n" +"Name :" + fname +" "+lname + "\n" + "Age :"+ age + "\n" + "Gender: " + SpinnerGender.getSelectedItem()
                                + "\n" + "Location: "+ SpinnerLocation.getSelectedItem() + "\n" + "Disease Type: " + SpinnerDiseaseType.getSelectedItem();

                        message.add(data);
                        StringBuilder stringBuilder = new StringBuilder();
                        for( String s : message)
                        {
                            stringBuilder.append(s);
                            stringBuilder.append("\n");
                        }
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Phone, null, Message, null, null);
                            viewData(stringBuilder.toString()); // msg saved in shared pref. It can be viewed in view record activity.
                            Toast.makeText(getApplicationContext(),
                                    "Message Sent.", Toast.LENGTH_LONG).show();
                            TextFname.setText("");
                            TextlastName.setText("");
                            TextAge.setText("");
                            SpinnerGender.setSelection(0);
                            SpinnerLocation.setSelection(0);
                            SpinnerDiseaseType.setSelection(0);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),
                    "Failed.", Toast.LENGTH_LONG).show();
        }
    }   // onCreate close here.


                // }

        @Override
        public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(getApplicationContext(), "Permission Granted.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Permission not granted.", Toast.LENGTH_LONG).show();
                        //return;
                    }
                }
            }

        }

        public void viewData(String msg)
        {
            getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                    .edit()
                    .putString("detail",msg)
                    .commit();

        }
        public void onClick(View v){
            Intent in = new Intent(this,UserInterface.class);
            startActivity(in);
            finish();
        }

}
