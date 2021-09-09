package com.cust.alumini.broadcastreciever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        private static final int MY_PERMISSIONS_REQUEST_SMS =0;
        private static final String SMS_RECIEVED = "android.provider.Telephony.SMS_RECIEVED";
        TextView messagetv, numbertv;

        MyReceiver receiver = new MyReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                messagetv.setText(msg);
                numbertv.setText(phoneNo);
            }
        };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SMS_RECIEVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messagetv = findViewById(R.id.message);
        numbertv = findViewById(R.id.number);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_SMS);
            }
        }
    }
       @SuppressLint("MissingSuperCall")
    public void onRequestPermissionsResult(int requestcode, String permissions[], int [] grantResults ) {

           switch (requestcode) {
               case MY_PERMISSIONS_REQUEST_SMS: {
                   if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                       Toast.makeText(this, "Thank you for permission", Toast.LENGTH_LONG).show();
                   } else {
                       Toast.makeText(this, "Well I can't do any thing until you permit me", Toast.LENGTH_LONG).show();
                   }
               }
           }
       }
}