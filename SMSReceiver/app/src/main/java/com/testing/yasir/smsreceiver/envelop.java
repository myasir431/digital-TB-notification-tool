package com.testing.yasir.smsreceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Yasir on 4/4/2018.
 */

public class envelop {
    String message;

    // constructor
    public envelop(String msg){
        this.message   = msg;

    }

    // lets put data in envelop
    public String dataEnvelop(){

        try {
            JSONObject json = new JSONObject();
            StringBuffer buf = new StringBuffer();

            json.put("detail", message);


            Boolean firstValue = true;
            Iterator it = json.keys();
            do {
                String key = it.next().toString();
                String value = json.get(key).toString();

                if (firstValue){
                    firstValue = false;
                }
                else{
                    buf.append("&");
                }

                buf.append(URLEncoder.encode(key,"UTF-8"));
                buf.append("=");
                buf.append(URLEncoder.encode(value,"UTF-8"));
            }while (it.hasNext());

            return buf.toString();

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
}
