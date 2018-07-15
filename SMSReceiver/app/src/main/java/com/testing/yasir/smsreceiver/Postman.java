package com.testing.yasir.smsreceiver;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

/**
 * Created by Yasir on 4/4/2018.
 */

public class Postman extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress,msg;


    String name,diss,lvl,sex;
    ProgressDialog pd;

    public Postman(Context c, String urlAddress, String msg){

        this.msg = msg;

        this.c = c;
        this.urlAddress = urlAddress;


        //  lvl = level.getText().toString();
        //  diss = disease.getText().toString();
        //   sex = gendr.getText().toString();

    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Send");
        pd.setMessage("Sending...Please wait.");
        pd.show();
    }

    // Sending data to network.
    @Override
    protected String doInBackground(Void... params){
        return this.send();
    }

    /*
    1-Called when job is done.
    2-dismiss the progress dialogue.
    3-receive string from DoInBackGround
     */
    @Override
    protected void onPostExecute(String response){
        super.onPostExecute(response);

        pd.dismiss();
        //Toast.makeText(c,response,Toast.LENGTH_s).show();
        if (response != null){

            Toast.makeText(c,response,Toast.LENGTH_SHORT).show();


        }
        else{
            Toast.makeText(c,"Unsuccessful "+response,Toast.LENGTH_LONG).show();
        }
    }

    /*
    sending data and receiving response.
     */
    private String send() {
        //remember we created connect class.
        HttpURLConnection con = connector.connect(urlAddress);

        if (con == null) {
            return null;
        }
        try {
            OutputStream outputStream = con.getOutputStream();

            //write
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            // putting data into envelop
            bw.write(new envelop(msg).dataEnvelop());

            //Release Resources
            bw.flush();;
            bw.close();
            bw.close();
            outputStream.close();

            //testing if successful
            int responseCode = con.getResponseCode();
            if (responseCode==con.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line;
                // read line by line.
                while((line = br.readLine()) != null){
                    response.append(line);
                }

                br.close();
                return response.toString();

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
