package com.testing.yasir.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.provider.Telephony;
import android.widget.Toast;

/**
 * Created by Yasir on 4/4/2018.
 */

public class Receiver extends BroadcastReceiver {
    String str;
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        SmsMessage message=null;

        if (bundle != null) {
            //pdus is programable description unit here.
            Object[] pdus = (Object[]) bundle.get("pdus");
            //message = new SmsMessage[pdus != null ? pdus.length:0];

            String senderNumber = null;
            for (int i = 0; i < pdus.length; i++) {
                message = SmsMessage.createFromPdu((byte[]) (pdus[i]));

                senderNumber = message.getOriginatingAddress();
                String msgbody = message.getDisplayMessageBody();
                str = senderNumber+" , "+msgbody;
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }

            //SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(senderNumber, null, "Thank you for your Input.", null, null);

            Intent intent1 = new Intent(context,MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("sms",str);
            context.startActivity(intent1);
        }
    }
}