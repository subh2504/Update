package update.core.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by subhashhardaha on 01/12/17.
 */

public class UpdateReceiver extends BroadcastReceiver {


        public static final String SMS_BUNDLE = "pdus";

        public void onReceive(Context context, Intent intent) {
            Bundle intentExtras = intent.getExtras();
            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
                String smsMessageStr = "";
                for (int i = 0; i < sms.length; ++i) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    String smsBody = smsMessage.getMessageBody().toString();
                    String sender = smsMessage.getOriginatingAddress();
                    Long timeStamp = smsMessage.getTimestampMillis();

                    //smsMessageStr += "SMS From: " + address + "\n";
                    //smsMessageStr += smsBody + "\n";
                }
                //Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
                context.startService(new Intent(context,AutoStart.class));
                //this will update the UI with message
                //SmsActivity inst = SmsActivity.instance();
                //inst.updateList(smsMessageStr);
            }
        }

        // Retrieves a map of extended data from the intent.
    }
