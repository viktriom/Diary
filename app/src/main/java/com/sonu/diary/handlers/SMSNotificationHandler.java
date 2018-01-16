package com.sonu.diary.handlers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by sonu on 05/12/16.
 */

public class SMSNotificationHandler extends BroadcastReceiver {

    public SMSNotificationHandler(){
    }


    public void onReceive(Context context, Intent intent){
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if(bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            if(null == pdus)
                return;
            msgs = new SmsMessage[pdus.length];
            for(int i = 0; i <msgs.length;i ++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                if(null == msgs[i])
                    continue;
                str+=msgs[i].getMessageBody();
            }
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        }
    }
}
