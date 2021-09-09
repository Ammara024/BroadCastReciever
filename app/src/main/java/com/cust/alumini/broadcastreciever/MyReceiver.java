package com.cust.alumini.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECIEVED = "android.provider.Telephony.SMS_RECIEVED";
    private static final String TAG = "SmsBroadCastReciever";
    String msg, phoneNo="";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent Reciever: " + intent.getAction());
         if(intent.getAction() == SMS_RECIEVED)
        {
            Bundle databundle = intent.getExtras();
            if (databundle !=null)
            {
                //PDU PTOTOCAL DATA UNIT
                Object [] mypdu = (Object[])databundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for(int i=0; i< mypdu.length; i++)
                {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        String format = databundle.getString("formate");
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    }
                    else
                    {
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                }
                Toast.makeText(context, "Message : " + msg + "\nNumber : " + phoneNo, Toast.LENGTH_SHORT).show();
            }
        }
    }
}